package com.example.project2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class bookmarkAdapter extends BaseAdapter {
    private Context context;
    private List<bookmark> bookmarkList;

    public bookmarkAdapter(Context context, List<bookmark> bookmarkList){
        this.context = context;
        this.bookmarkList= bookmarkList;
    }

    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookmarkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.bookmark,null);
        TextView userID = (TextView)v.findViewById(R.id.userID);
        TextView location = (TextView)v.findViewById(R.id.location);
        TextView address = (TextView)v.findViewById(R.id.address);


        userID.setText(bookmarkList.get(position).getUserID());
        location.setText(bookmarkList.get(position).getLocation());
        address.setText(bookmarkList.get(position).getAddress());

        v.setTag(bookmarkList.get(position).getLocation());

        return v;
    }
}
