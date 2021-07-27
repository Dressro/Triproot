package com.example.project2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class commentListAdapter extends BaseAdapter {
    private Context context;
    private List<comment> registerList;
    public commentListAdapter(Context context , List<comment> registerList){
        this.context = context;
        this.registerList = registerList;
    }
    @Override
    public int getCount() {
        return registerList.size();
    }

    @Override
    public Object getItem(int position) {
        return registerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.comment,null);
        TextView register = (TextView)v.findViewById(R.id.register);
        TextView commnet = (TextView)v.findViewById(R.id.comment);

        register.setText(registerList.get(position).getRegister());
        commnet.setText(registerList.get(position).getComment());

        v.setTag(registerList.get(position).getRegister());
        return v;
    }
}
