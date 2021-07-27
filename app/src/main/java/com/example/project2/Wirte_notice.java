package com.example.project2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class Wirte_notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_notice);
        final EditText noticeTitleText =(EditText)findViewById(R.id.noticeTitle);
        final EditText noticeText = (EditText)findViewById(R.id.notice);
        Button noticeButton = (Button) findViewById(R.id.noticeButton);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notice = noticeText.getText().toString();
                String noticeTitle = noticeTitleText.getText().toString();
                Response.Listener<String> respon = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json = new JSONObject(response);
                            Boolean suceess = json.getBoolean("success");
                            if(suceess){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Wirte_notice.this);
                                builder.setMessage("공지 등록이 완료 되었습니다.")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent1 = new Intent(Wirte_notice.this,MainWatch.class);
                                                intent1.putExtra("userID",userID);
                                                Wirte_notice.this.startActivity(intent1);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Wirte_notice.this);
                                builder.setMessage("공지 등록이 실패하였습니다.")
                                        .setNegativeButton("확인",null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                Wirte_notice_Request request = new Wirte_notice_Request(notice,noticeTitle,respon);
                RequestQueue queue = Volley.newRequestQueue(Wirte_notice.this);
                queue.add(request);

            }
        });
    }
}
