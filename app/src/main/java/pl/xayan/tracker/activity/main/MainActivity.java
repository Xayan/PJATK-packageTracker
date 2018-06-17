package pl.xayan.tracker.activity.main;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pl.xayan.tracker.R;
import pl.xayan.tracker.activity.parcel.AddPackageActivity;
import pl.xayan.tracker.activity.parcel.ParcelEventListFragment;
import pl.xayan.tracker.activity.parcel.ParcelListFragment;
import pl.xayan.tracker.activity.settings.SettingsActivity;
import pl.xayan.tracker.db.entity.Parcel;

public class MainActivity extends FragmentActivity implements ParcelListFragment.OnParcelSelectListener {
    private ParcelListFragment parcelListFragmentOnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            ParcelListFragment parcelListFragment = new ParcelListFragment();
            parcelListFragment.setArguments(getIntent().getExtras());

            parcelListFragmentOnCreate = parcelListFragment;

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, parcelListFragment).commit();
        } else {
            parcelListFragmentOnCreate = (ParcelListFragment)
                    getFragmentManager().findFragmentById(R.id.headlines_fragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParcelListFragment parcelListFragment = (ParcelListFragment)
                getFragmentManager().findFragmentById(R.id.headlines_fragment);

        try {
            parcelListFragment.loadPackages();
        } catch (Exception e) {
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

        switch (item.getItemId()) {
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

    public void onParcelSelect(int position) {
        ParcelEventListFragment parcelEventListFragment = (ParcelEventListFragment)
                getFragmentManager().findFragmentById(R.id.webviewfragment);

        if (parcelEventListFragment != null) {
            Parcel parcel = parcelListFragmentOnCreate.getParcelList().get(position);

            Bundle bundle = new Bundle();
            bundle.putInt(ParcelEventListFragment.PACKAGE_ID_KEY, parcel.getId());
            parcelEventListFragment.setArguments(bundle);

            try {
                parcelEventListFragment.loadDetails();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            ParcelEventListFragment newFragment = new ParcelEventListFragment();

            Parcel parcel = parcelListFragmentOnCreate.getParcelList().get(position);

            Bundle bundle = new Bundle();
            bundle.putInt(ParcelEventListFragment.PACKAGE_ID_KEY, parcel.getId());

            newFragment.setArguments(bundle);

            try {
                newFragment.loadDetails();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
