package com.example.project2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class noticeAdapter extends BaseAdapter {
    private Context context;
    private List<notice> noticeList;

    public noticeAdapter(Context context, List<notice> noticeList){
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.notice,null);
        TextView notice = (TextView)v.findViewById(R.id.notice);


        notice.setText(noticeList.get(position).getNoticeTitle());

        v.setTag(noticeList.get(position).getNoticeTitle());

        return v;
    }
}
