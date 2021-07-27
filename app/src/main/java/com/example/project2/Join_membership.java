package com.example.project2;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.RunnableScheduledFuture;

public class Join_membership extends AppCompatActivity {
    boolean success2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_membership);

        final EditText idText = (EditText)findViewById(R.id.editText3);
        final EditText passwordText = (EditText)findViewById(R.id.editText4);
        final TextView name = (TextView)findViewById(R.id.name);
        success2 = false;
        Button button2 = (Button)findViewById(R.id.button12);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json = new JSONObject(response);
                            boolean success = json.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join_membership.this);
                                builder.setMessage("아이디 사용이 가능합니다.")
                                        .setNegativeButton("확인",null)
                                        .create()
                                        .show();
                                success2 = true;
                                name.setText("아이디 중복확인 완료");
                                name.setTextColor(Color.parseColor("#00FF00"));
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join_membership.this);
                                builder.setMessage("아이디 사용이 불가능합니다.")
                                        .setNegativeButton("확인",null)
                                        .create()
                                        .show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                Join_membership_Request2 join2 = new Join_membership_Request2(userID,res);
                RequestQueue qu2 = Volley.newRequestQueue(getApplicationContext());
                qu2.add(join2);
            }
        });

        Button button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=idText.getText().toString();
                String Password=passwordText.getText().toString();

                Response.Listener<String> respon = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject json = new JSONObject(response);
                            boolean success = json.getBoolean("success");
                            if(success && success2){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join_membership.this);
                                builder.setMessage("회원가입이 완료 되었습니다")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                            else if(success && !success2){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join_membership.this);
                                builder.setMessage("아이디 중복확인을 하세요")
                                        .setNegativeButton("다시시도",null)
                                        .create()
                                        .show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join_membership.this);
                                builder.setMessage("아이디 생성이 불가능 합니다.")
                                        .setNegativeButton("다시시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                 Join_membership_Request join = new Join_membership_Request(userID,Password,respon);
                 RequestQueue qu = Volley.newRequestQueue(getApplicationContext());
                 qu.add(join);
            }
        });

    }

}
