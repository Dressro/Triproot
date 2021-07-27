package com.example.project2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainWatch extends AppCompatActivity {
    private  ListView listView;
    private noticeAdapter adapter;
    private List<notice> noticeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_watch);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        //String Password = intent.getStringExtra("Password");
        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        final View drawerView = (View)findViewById(R.id.page);
        listView = (ListView)findViewById(R.id.noticelist);
        noticeList = new ArrayList<notice>();
        adapter = new noticeAdapter(getApplicationContext(),noticeList);
        listView.setAdapter(adapter);
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("noticeList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String noticeTitle;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                noticeTitle = object.getString("noticeTitle");
                notice notice = new notice(noticeTitle);
                noticeList.add(notice);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(MainWatch.this,see_notice.class);
                intent2.putExtra("noticeTitle",noticeList.get(position).noticeTitle);
                startActivity(intent2);
            }
        });

        Button tripbutton = (Button)findViewById(R.id.button5);
        tripbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask2().execute();
            }
        });
        Button writebutton = (Button)findViewById(R.id.button8);
        if(!userID.equals("root")){
            writebutton.setEnabled(false);
            writebutton.setVisibility(View.INVISIBLE);
        }
        writebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainWatch.this,Wirte_notice.class);
                intent2.putExtra("userID",userID);
                MainWatch.this.startActivity(intent2);
            }
        });

        Button rebutton =(Button)findViewById(R.id.button4);
        rebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

    }

    class BackgroundTask extends AsyncTask<Void, Void,String>{ //리스트 접속
        String target;
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        @Override
        protected void onPreExecute(){
            target = "http://14.49.39.152/TRIP/locat.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp=bufferedReader.readLine())!=null){
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();;
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){
            Intent intent3 = new Intent(MainWatch.this,information.class);
            intent3.putExtra("userID",userID);
            intent3.putExtra("locationList",result);
            MainWatch.this.startActivity(intent3);
        }
    }
    class BackgroundTask2 extends AsyncTask<Void, Void,String>{ //리스트 접속
        String target;
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        @Override
        protected void onPreExecute(){
            target = "http://14.49.39.152/TRIP/bookmark.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp=bufferedReader.readLine())!=null){
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();;
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){
            Intent intent4 = new Intent(MainWatch.this,TripRoot.class);
            intent4.putExtra("userID",userID);
            intent4.putExtra("locationList",result);
            MainWatch.this.startActivity(intent4);
        }
    }
}
