package com.example.project2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class commentView extends AppCompatActivity {
    private  ListView listView;
    private  commentListAdapter adapter;
    private List<comment> commentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        Intent intent = getIntent();
        final String address2 = intent.getStringExtra("address");
        final String regUserID = intent.getStringExtra("regUserID");
        final String userID = intent.getStringExtra("userID");
        Button finishbutton = (Button) findViewById(R.id.finishbutton);
        listView = (ListView) findViewById(R.id.commentlist);
        commentList = new ArrayList<comment>();
        adapter = new commentListAdapter(getApplicationContext(), commentList);
        listView.setAdapter(adapter);
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("commentList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String register;
            String comments;
            String address3;

            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                register = object.getString("register");
                comments = object.getString("comments");
                address3 = object.getString("address");
                if(address2.equals(address3)) {
                    comment location1 = new comment(comments,register);
                    commentList.add(location1);
                }
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button3 = (Button) findViewById(R.id.commentbutton);
        final TextView commentText = (TextView) findViewById(R.id.comment);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = commentText.getText().toString();
                String register = regUserID;
                Response.Listener<String> respon3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(commentView.this);
                                builder.setMessage("댓글 등록이 되었습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                                new BackgroundTask5().execute();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(commentView.this);
                                builder.setMessage("댓글 등록 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
               commentView_Request3 commentView_request3 = new commentView_Request3(register, comments, address2, respon3);
                RequestQueue requestQueue3 = Volley.newRequestQueue(commentView.this);
                requestQueue3.add(commentView_request3);

            }
        });
    }
    class BackgroundTask5 extends AsyncTask<Void, Void,String> { //리스트 접속
        String target;
        Intent intent = getIntent();
        String address2 = intent.getStringExtra("address");
        String regUserID = intent.getStringExtra("regUserID");
        String userID = intent.getStringExtra("userID");

        @Override
        protected void onPreExecute() { target = "http://14.49.39.152/TRIP/commentGet.php"; }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;

        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){
            Intent intent4 = new Intent(getApplicationContext(),commentView.class);
            intent4.putExtra("address",address2);
            intent4.putExtra("regUserID",regUserID);
            intent4.putExtra("userID",userID);
            intent4.putExtra("commentList",result);
            getApplicationContext().startActivity(intent4);
        }

    }
}
