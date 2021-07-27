package com.example.project2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class see_notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_notice);
        Intent intent = getIntent();
        String noticeTitleText = intent.getStringExtra("noticeTitle");
        final TextView enternoticeText = (TextView)findViewById(R.id.enternotice);
        final TextView enternoticeTitleText = (TextView)findViewById(R.id.enternoticetitle);
        Button enterbutton = (Button)findViewById(R.id.enter);
        Response.Listener<String> respon = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean success = json.getBoolean("success");
                    if(success){
                        String notice = json.getString("notice");
                        String noticeTitle = json.getString("noticeTitle");
                        enternoticeText.setText(notice);
                        enternoticeTitleText.setText(noticeTitle);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(see_notice.this);
                        builder.setMessage("정보가 없습니다.")
                                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .create()
                                .show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        see_notice_Request see_notice_request = new see_notice_Request(noticeTitleText,respon);
        RequestQueue queue = Volley.newRequestQueue(see_notice.this);
        queue.add(see_notice_request);
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
