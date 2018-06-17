package pl.xayan.tracker.activity.parcel;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.xayan.tracker.R;
import pl.xayan.tracker.activity.main.ListAdapter;
import pl.xayan.tracker.db.AppDatabase;
import pl.xayan.tracker.db.entity.Event;
import pl.xayan.tracker.db.entity.Parcel;
import pl.xayan.tracker.service.AftershipApiService;

public class ParcelEventListFragment extends ListFragment {
    public static final String PACKAGE_ID_KEY = "PACKAGE_ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            loadDetails();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void loadDetails() throws Exception {
        Bundle bundle = this.getArguments();
        int packageId = bundle.getInt(PACKAGE_ID_KEY, 0);

        PackageDetailsActivityAsyncTask task = new PackageDetailsActivityAsyncTask(getActivity(), packageId);

        final List<Event> eventList = task.execute().get();

        setListAdapter(new EventListAdapter(getActivity(), eventList));
    }

    private static class PackageDetailsActivityAsyncTask extends AsyncTask<Void, Void, List<Event>> {
        private AftershipApiService aftershipApiService;
        private int packageId;

        public PackageDetailsActivityAsyncTask(Activity activity, int packageId) {
            this.aftershipApiService = new AftershipApiService();
            this.packageId = packageId;
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            return aftershipApiService.getEvents(packageId);
        }
    }
}
