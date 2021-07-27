package com.example.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TripRoot extends AppCompatActivity {
    private ListView listView;
    private  bookmarkAdapter adapter;
    private List<bookmark> bookmarkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_root);
        Intent intent = getIntent();
        final String regUserID =  intent.getStringExtra("userID");
        listView = (ListView)findViewById(R.id.rootlist);
        bookmarkList = new ArrayList<bookmark>();
        adapter = new bookmarkAdapter(getApplicationContext(),bookmarkList);
        listView.setAdapter(adapter);
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("locationList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID;
            String location;
            String address;
            String regUser;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                location = object.getString("location");
                address = object.getString("address");
                regUser = object.getString("regUser");
                if(regUserID.equals(regUser)) {
                    bookmark bookmark1 = new bookmark(userID, location, address);
                    bookmarkList.add(bookmark1);
                }
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getApplicationContext(),locationevaluation.class);
                intent2.putExtra("regUserID",regUserID); //접속자 아이디 보내기
                intent2.putExtra("userID",bookmarkList.get(position).userID); // 등록자 아이디 보내기
                intent2.putExtra("address",bookmarkList.get(position).address);
                intent2.putExtra("success",false);
                startActivity(intent2);
            }
        });
    }
}
