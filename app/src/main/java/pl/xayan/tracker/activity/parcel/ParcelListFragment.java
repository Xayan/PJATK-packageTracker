package pl.xayan.tracker.activity.parcel;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import pl.xayan.tracker.activity.main.ListAdapter;
import pl.xayan.tracker.db.AppDatabase;
import pl.xayan.tracker.db.entity.Parcel;

public class ParcelListFragment extends android.app.ListFragment {
    private OnParcelSelectListener mCallback;

    private List<Parcel> parcelList;

    public interface OnParcelSelectListener {
        void onParcelSelect(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            loadPackages();
        } catch(Exception e) {

        }
    }

    public void loadPackages() throws Exception {
        final Activity activity = this.getActivity();

        GetPackagesAsyncTask task = new GetPackagesAsyncTask(activity);

        parcelList = task.execute().get();

        setListAdapter(new ListAdapter(getActivity(), parcelList));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnParcelSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWebAddressSelectedListener");
        }
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onParcelSelect(position);
        getListView().setItemChecked(position, true);
    }

    public List<Parcel> getParcelList() {
        return parcelList;
    }
}
