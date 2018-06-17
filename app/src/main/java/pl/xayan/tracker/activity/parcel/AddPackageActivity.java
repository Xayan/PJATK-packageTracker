package pl.xayan.tracker.activity.parcel;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import pl.xayan.tracker.R;
import pl.xayan.tracker.db.AppDatabase;
import pl.xayan.tracker.db.entity.Parcel;
import pl.xayan.tracker.service.AftershipApiService;

public class AddPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);
    }

    public void addPackage(View view) {
        EditText numberField = findViewById(R.id.addPackageNumber);
        EditText labelField = findViewById(R.id.addPackageLabel);

        AddPackageAsyncTask task = new AddPackageAsyncTask(
                this,
                numberField.getText().toString(),
                labelField.getText().toString());

        Parcel parcel = null;

        try {
            parcel = task.execute().get();
        } catch (Exception e) {
            System.err.println(e.getMessage());

            Toast.makeText(this, "There was an error, try again", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Package was added successfully", Toast.LENGTH_SHORT).show();

        finish();
    }

    private static class AddPackageAsyncTask extends AsyncTask<Void, Void, Parcel> {
        private WeakReference<Activity> activityWeakReference;
        private AppDatabase appDatabase;
        private AftershipApiService apiService;
        private String number;
        private String label;

        public AddPackageAsyncTask(Activity activity, String number, String label) {
            this.activityWeakReference = new WeakReference<Activity>(activity);
            this.appDatabase = AppDatabase.getInstance(activityWeakReference.get().getApplicationContext());
            this.apiService = new AftershipApiService();

            this.number = number;
            this.label = label;
        }

        @Override
        protected Parcel doInBackground(Void... voids) {
            final Activity activity = activityWeakReference.get();

            try {
                Parcel parcel = apiService.addTracking(this.number, this.label);

                if (parcel != null) {
                    appDatabase.parcelDao().insert(parcel);

                    Intent intent = new Intent(activity.getApplicationContext(), PackageDetailsActivity.class);
                    intent.putExtra(PackageDetailsActivity.PACKAGE_ID_KEY, parcel.getId());

                    return parcel;
                } else {
                    throw new Exception("Parcel was not added");
                }
            } catch (Exception e) {
                final String message = "Error: " + e.getMessage() + "(" + e.getClass().getName() + ")";

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
    }
}
