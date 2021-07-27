package com.example.project2;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText idText;
    EditText passText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idText = (EditText)findViewById(R.id.editText);
        passText = (EditText)findViewById(R.id.editText2);
        Button Loginbutton = (Button) findViewById(R.id.button);
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                String Password = passText.getText().toString();
                Response.Listener<String> respon = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("로그인에 성공했습니다.")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new noticeBackgroundTask().execute();
                                            }
                                        })
                                        .create()
                                        .show();


                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("아이디와 비밀번호를 다시 확인하세요")
                                        .setNegativeButton("확인",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Login_Request login_request =  new Login_Request(userID,Password,respon);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(login_request);
            }
        });
        Button join_membershipbutton = (Button) findViewById(R.id.button2);
        join_membershipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,Join_membership.class);
                MainActivity.this.startActivity(intent2);
            }
        });
    }
    class noticeBackgroundTask extends AsyncTask<Void, Void,String> { //리스트 접속
        String target;
        String userID = idText.getText().toString();
        String Password = passText.getText().toString();
        @Override
        protected void onPreExecute(){
            target = "http://14.49.39.152/TRIP/notice_get.php";
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
            Intent intent = new Intent(MainActivity.this,MainWatch.class);
            intent.putExtra("userID",userID);
            intent.putExtra("noticeList",result);
            MainActivity.this.startActivity(intent);
            finish();
        }
    }
}