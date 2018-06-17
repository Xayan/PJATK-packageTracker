package pl.xayan.tracker.activity.parcel;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import pl.xayan.tracker.R;
import pl.xayan.tracker.db.AppDatabase;
import pl.xayan.tracker.db.entity.Parcel;

public class PackageDetailsActivity extends AppCompatActivity {

    public static final String PACKAGE_ID_KEY = "PACKAGE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        int packageId = getIntent().getExtras().getInt(PACKAGE_ID_KEY, 0);
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

        PackageDetailsActivityAsyncTask task = new PackageDetailsActivityAsyncTask(this, packageId);
        task.execute();
    }

    private static class PackageDetailsActivityAsyncTask extends AsyncTask<Void, Void, Parcel> {
        private WeakReference<Activity> activityWeakReference;
        private AppDatabase appDatabase;
        private int packageId;

        public PackageDetailsActivityAsyncTask(Activity activity, int packageId) {
            this.activityWeakReference = new WeakReference<Activity>(activity);
            this.appDatabase = AppDatabase.getInstance(activityWeakReference.get().getApplicationContext());
            this.packageId = packageId;
        }

        @Override
        protected Parcel doInBackground(Void... voids) {
            return this.appDatabase.parcelDao().findById(packageId);
        }

        @Override
        protected void onPostExecute(Parcel parcel) {
            Activity activity = activityWeakReference.get();

            activity.setTitle(parcel.getLabel() + " - " + parcel.getTrackingNumber());

            if(parcel == null) {
                Toast.makeText(activity.getApplicationContext(), "Parcel not found", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity.getApplicationContext(), "Parcel found: " + parcel.getTrackingNumber(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
