package com.example.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class information extends AppCompatActivity {
    private  ListView listView;
    private  locationListAdapter adapter;
    private List<location> locationList;
    private RelativeLayout page;
    Button recommend;
    Animation translateup;
    Animation translatedown;
    boolean pageopen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        final Intent intent = getIntent();
        final String regUserID =  intent.getStringExtra("userID");
        final Button writeButton2 = (Button)findViewById(R.id.write);
        translateup = AnimationUtils.loadAnimation(this,R.anim.translate_up);
        translatedown = AnimationUtils.loadAnimation(this,R.anim.translate_down);
        SlidingAnimationLister lister = new SlidingAnimationLister();
        translateup.setAnimationListener(lister);
        translatedown.setAnimationListener(lister);
        listView = (ListView)findViewById(R.id.listView);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner2);
        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner3);
        page = findViewById(R.id.page);
        page.setVisibility(View.INVISIBLE);
        Button button13 = (Button)findViewById(R.id.button13);
        recommend = (Button)findViewById(R.id.recommend);
        locationList = new ArrayList<location>();
        adapter = new locationListAdapter(getApplicationContext(),locationList);
        listView.setAdapter(adapter);
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("locationList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID;
            String location;
            String address;
            String rating;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                location = object.getString("location");
                address = object.getString("address");
                rating = object.getString("rating");
                location location1 = new location(userID,location,address,rating);
                locationList.add(location1);
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
                intent2.putExtra("userID",locationList.get(position).userID); // 등록자 아이디 보내기
                intent2.putExtra("address",locationList.get(position).address);
                intent2.putExtra("success",true);
                startActivity(intent2);
            }
        });
        writeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(information.this,Write.class);
                intent2.putExtra("userID",regUserID);
                information.this.startActivity(intent2);
            }
        });
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page.setVisibility(View.VISIBLE);
                page.startAnimation(translatedown);
                writeButton2.setVisibility(View.INVISIBLE);
                recommend.setVisibility(View.INVISIBLE);
            }
        });
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("locationList"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String userID;
                    String location;
                    String address;
                    String rating;
                    while(count < jsonArray.length()){
                        JSONObject object = jsonArray.getJSONObject(count);
                        userID = object.getString("userID");
                        location = object.getString("location");
                        address = object.getString("address");
                        rating = object.getString("rating");
                        location location1 = new location(userID,location,address,rating);
                        if(Float.parseFloat(rating) >= Float.parseFloat(spinner.getSelectedItem().toString())) {
                            if(location.equals(spinner2.getSelectedItem().toString())) {
                                locationList.add(location1);
                            }
                        }
                        count++;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                page.startAnimation(translateup);
                writeButton2.setVisibility(View.VISIBLE);
                recommend.setVisibility(View.VISIBLE);
                pageopen=true;
                adapter.notifyDataSetChanged();
            }
        });
    }
    class SlidingAnimationLister implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(pageopen){
                page.setVisibility(View.INVISIBLE);
                pageopen = false;
            }else{
                pageopen=true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}

