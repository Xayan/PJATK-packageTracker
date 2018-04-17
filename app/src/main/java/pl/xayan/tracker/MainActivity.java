package pl.xayan.tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.xayan.tracker.entity.Package;

public class MainActivity extends AppCompatActivity {ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        List<Package> packageList = new ArrayList<>();

        Package p1 = new Package();
        p1.setTrackingNumber("123");
        Package p2 = new Package();
        p2.setTrackingNumber("123");
        Package p3 = new Package();
        p3.setTrackingNumber("123");
        Package p4 = new Package();
        p4.setTrackingNumber("123");
        Package p5 = new Package();
        p5.setTrackingNumber("123");
        Package p6 = new Package();
        p6.setTrackingNumber("123");

        packageList.add(p1);
        packageList.add(p2);
        packageList.add(p3);
        packageList.add(p4);
        packageList.add(p5);
        packageList.add(p6);

        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), packageList);
        listView.setAdapter(listAdapter);
    }

    public void onAddClick(View view) {
        Toast.makeText(this, "Add pressed", Toast.LENGTH_LONG).show();
    }
}
