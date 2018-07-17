package com.example.goyal.todo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter {
    ArrayList <Activity> item;
    LayoutInflater inflater;

    public ActivityAdapter(@NonNull Context context, ArrayList<Activity>item) {

        super(context,0,item);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item=item;
    }

    @Override
    public int getCount()
    {
        return item.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output= convertView;
        if(output==null)
        {
            output = inflater.inflate(R.layout.activity_row_layout, parent, false);  //we get view from this....................................................................in this false is used because we don't want inflater to attach views but we want list view to set it.
            TextView nameTextView = output.findViewById(R.id.text1);
            TextView dateTextView = output.findViewById(R.id.date1);
            TextView timeTextView = output.findViewById(R.id.time1);//find the parameters for the view
            ActivityHolder activityHolder=new ActivityHolder();
            activityHolder.activity=nameTextView;
            activityHolder.date=dateTextView;
            activityHolder.time=timeTextView;
            output.setTag(activityHolder);

        }
        ActivityHolder activityHolder = (ActivityHolder)output.getTag();
        Activity activity = item.get(position);
        activityHolder.activity.setText(activity.getTodo());
        activityHolder.date.setText(activity.getDate());
        activityHolder.time.setText(activity.getTime());
        return output;
    }
}
