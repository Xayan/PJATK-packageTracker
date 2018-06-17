package pl.xayan.tracker.activity.parcel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.xayan.tracker.R;
import pl.xayan.tracker.db.entity.Event;

public class EventListAdapter extends ArrayAdapter<Event> implements View.OnClickListener {

    private Context context;
    private List<Event> eventList = new ArrayList<>();

    public EventListAdapter(Context context, List<Event> data) {
        super(context, -1, data);

        this.context = context;
        this.eventList = data;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_event, parent, false);

        TextView listItemNumber = rowView.findViewById(R.id.list_item_number);
        TextView listItemDescription = rowView.findViewById(R.id.list_item_description);

        Event event = eventList.get(position);

        listItemNumber.setText(event.getDate());
        listItemDescription.setText(event.getMessage());

        return rowView;
    }
}
