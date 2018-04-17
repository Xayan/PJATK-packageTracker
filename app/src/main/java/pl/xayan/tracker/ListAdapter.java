package pl.xayan.tracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.xayan.tracker.entity.Package;

public class ListAdapter extends ArrayAdapter<Package> implements View.OnClickListener {

    private Context context;
    private List<Package> packageList = new ArrayList<>();

    public ListAdapter(Context context, List<Package> data) {
        super(context, -1, data);

        this.context = context;
        this.packageList = data;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Yo", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView listItemNumber = rowView.findViewById(R.id.list_item_number);
//        listItemNumber.setText(packageList.get(position).getTrackingNumber());

        return rowView;
    }
}
