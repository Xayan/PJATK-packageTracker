package pl.xayan.tracker.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.xayan.tracker.R;
import pl.xayan.tracker.activity.settings.SettingsActivity;
import pl.xayan.tracker.activity.packageDetails.PackageDetailsActivity;
import pl.xayan.tracker.db.entity.Parcel;

public class MainActivity extends AppCompatActivity {ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        List<Parcel> packageList = new ArrayList<>();

        Parcel p1 = new Parcel();
        p1.setTrackingNumber("123");
        Parcel p2 = new Parcel();
        p2.setTrackingNumber("123");
        Parcel p3 = new Parcel();
        p3.setTrackingNumber("123");
        Parcel p4 = new Parcel();
        p4.setTrackingNumber("123");
        Parcel p5 = new Parcel();
        p5.setTrackingNumber("123");
        Parcel p6 = new Parcel();
        p6.setTrackingNumber("123");
        Parcel p7 = new Parcel();
        p7.setTrackingNumber("123");
        Parcel p8 = new Parcel();
        p8.setTrackingNumber("123");
        Parcel p9 = new Parcel();
        p9.setTrackingNumber("123");
        Parcel p10 = new Parcel();
        p10.setTrackingNumber("123");

        packageList.add(p1);
        packageList.add(p2);
        packageList.add(p3);
        packageList.add(p4);
        packageList.add(p5);
        packageList.add(p6);
        packageList.add(p7);
        packageList.add(p8);
        packageList.add(p9);
        packageList.add(p10);

        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), packageList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PackageDetailsActivity.class);
                intent.putExtra(PackageDetailsActivity.PACKAGE_ID_KEY, position);

                startActivity(intent);
            }
        });
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
            case R.id.menu_refresh:

                return true;
            case R.id.menu_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAddClick(View view) {
        Toast.makeText(this, "Add pressed", Toast.LENGTH_SHORT).show();
    }
}
