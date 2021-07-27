package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class locationListAdapter extends BaseAdapter {
    private Context context;
    private List<location> locationList;

    public locationListAdapter(Context context, List<location> locationList){
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.locationinform,null);
        TextView userID = (TextView)v.findViewById(R.id.userID);
        TextView location = (TextView)v.findViewById(R.id.location);
        TextView address = (TextView)v.findViewById(R.id.address);
        RatingBar ratingBar = (RatingBar)v.findViewById(R.id.ratingBar2);


        userID.setText(locationList.get(position).getUserID());
        location.setText(locationList.get(position).getLocation());
        address.setText(locationList.get(position).getAddress());
        ratingBar.setRating(Float.parseFloat(locationList.get(position).getRating()));

        v.setTag(locationList.get(position).getLocation());

        return v;
    }
}
