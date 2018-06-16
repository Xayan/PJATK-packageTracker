package pl.xayan.tracker.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import pl.xayan.tracker.R;
import pl.xayan.tracker.activity.parcel.AddPackageActivity;
import pl.xayan.tracker.activity.settings.SettingsActivity;
import pl.xayan.tracker.activity.parcel.PackageDetailsActivity;
import pl.xayan.tracker.db.AppDatabase;
import pl.xayan.tracker.db.entity.Parcel;
import pl.xayan.tracker.service.AftershipApiService;

public class MainActivity extends AppCompatActivity {ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            loadPackages();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            loadPackages();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(item.getItemId()) {
            case R.id.menu_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAddClick(View view) {
        Intent addPackageIntent = new Intent(MainActivity.this, AddPackageActivity.class);
        startActivity(addPackageIntent);
    }

    private static class GetPackagesAsyncTask extends AsyncTask<Void, Void, List<Parcel>> {
        private WeakReference<Activity> activityWeakReference;
        private AppDatabase appDatabase;

        public GetPackagesAsyncTask(Activity activity) {
            this.activityWeakReference = new WeakReference<Activity>(activity);
            this.appDatabase = AppDatabase.getInstance(activityWeakReference.get().getApplicationContext());
        }

        @Override
        protected List<Parcel> doInBackground(Void... voids) {
            return this.appDatabase.parcelDao().getAll();
        }
    }

    private void loadPackages() throws Exception {
        final Activity activity = this;

        GetPackagesAsyncTask task = new GetPackagesAsyncTask(activity);

        final List<Parcel> parcelList = task.execute().get();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = activity.findViewById(R.id.listview);

                ListAdapter listAdapter = new ListAdapter(activity.getApplicationContext(), parcelList);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(activity.getApplicationContext(), PackageDetailsActivity.class);
                        intent.putExtra(PackageDetailsActivity.PACKAGE_ID_KEY, parcelList.get(position).getId());

                        activity.startActivity(intent);
                    }
                });
            }
        });
    }
}
