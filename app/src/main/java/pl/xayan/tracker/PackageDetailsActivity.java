package pl.xayan.tracker;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class PackageDetailsActivity extends AppCompatActivity {

    public static final String PACKAGE_ID_KEY = "PACKAGE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        int packageId = getIntent().getExtras().getInt(PACKAGE_ID_KEY, 0);

        Toast.makeText(getApplicationContext(), "ID: " + packageId, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
