package pl.xayan.tracker.service;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.xayan.tracker.activity.parcel.PackageDetailsActivity;
import pl.xayan.tracker.db.AppDatabase;
import pl.xayan.tracker.db.entity.Event;
import pl.xayan.tracker.db.entity.Parcel;

public class AftershipApiService {
    private static final String API_ENDPOINT = "http://89.76.114.60";
    private static final String API_KEY = "b96685a8-dc68-44a4-8d50-2b328651e52c";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;

    public AftershipApiService() {
        httpClient = new OkHttpClient();
    }

    public boolean testConnection() {
        Request request = new Request.Builder()
                .url(API_ENDPOINT)
                .addHeader("aftership-api-key", API_KEY)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();

            JSONObject responseObject = new JSONObject(response.body().string());
            JSONObject metaObject = responseObject.getJSONObject("meta");

            int code = metaObject.getInt("code");

            return code == 200;
        } catch(Exception e) {
            System.err.println(e);

            return false;
        }

    }

    public Parcel addTracking(String number, String label) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number", number);
        jsonObject.put("label", label);

        Request request = new Request.Builder()
                .url(API_ENDPOINT + "/trackings")
                .post(RequestBody.create(JSON, jsonObject.toString()))
                .addHeader("aftership-api-key", API_KEY)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();

            JSONObject responseObject = new JSONObject(response.body().string());

            if(responseObject.getString("status").equals("error")) {
                return null;
            }

            Parcel parcel = new Parcel();
            parcel.setTrackingNumber(number);
            parcel.setLabel(label);
            parcel.setAftershipId(responseObject.getInt("id"));

            return parcel;
        } catch(Exception e) {
            System.err.println(e);

            return null;
        }
    }

    public List<Event> getEvents(int packageId) {
        List<Event> events = new ArrayList<>();

        Request request = new Request.Builder()
                .url(API_ENDPOINT + "/trackings/" + packageId)
                .addHeader("aftership-api-key", API_KEY)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();

            JSONObject responseObject = new JSONObject(response.body().string());

            if(responseObject.getString("status").equals("error")) {
                return events;
            }

            JSONArray eventArray = responseObject.getJSONArray("events");
            for(int i = 0; i < eventArray.length(); i++) {
                JSONObject eventObject = eventArray.getJSONObject(i);

                Event event = new Event();
                event.setDate(eventObject.getString("date"));
                event.setLocation(eventObject.getString("location"));
                event.setMessage(eventObject.getString("message"));

                events.add(event);
            }
        } catch(Exception e) {
            System.err.println(e);
        }

        return events;
    }
}
