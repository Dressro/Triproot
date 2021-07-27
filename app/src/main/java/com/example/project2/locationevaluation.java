package com.example.project2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class locationevaluation extends AppCompatActivity {
    ImageView locationImage;
    TextView addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationevaluation);
        Intent intent = getIntent();
        boolean read = false;
        String photo = null;
        boolean success = intent.getBooleanExtra("success",false);
        final String regUserID = intent.getStringExtra("regUserID"); // 접속자 아이디
        final String userID = intent.getStringExtra("userID"); // 등록자 아이디
        String address = intent.getStringExtra("address");
        Button button = (Button) findViewById(R.id.button9);
        Button button2 = (Button) findViewById(R.id.button10); //즐겨찾기 버튼
        Button button3 = (Button) findViewById(R.id.button11); //즐겨찾기 해제 버튼
        final TextView locationText = (TextView) findViewById(R.id.locationText);
        addressText = (TextView) findViewById(R.id.addressText);
        final TextView evalauationText = (TextView) findViewById(R.id.evlText);
        locationImage = (ImageView) findViewById(R.id.locationImage);
        Button imagebutton = (Button)findViewById(R.id.imagebutton);

        if(!success){
            button2.setEnabled(false);
            button2.setVisibility(View.INVISIBLE);
            button3.setEnabled(true);
        }else{
            button2.setEnabled(true);
            button3.setEnabled(false);
            button3.setVisibility(View.INVISIBLE);
        }
        Response.Listener<String> respon = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String evaluation = jsonObject.getString("evaluation");
                        String address = jsonObject.getString("address");
                        String location = jsonObject.getString("location");
                        locationText.setText(location);
                        addressText.setText(address);
                        evalauationText.setText(evaluation);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        final locationevaluation_Request locationevaluation_request = new locationevaluation_Request(userID, address, respon);
        RequestQueue queue = Volley.newRequestQueue(locationevaluation.this);
        queue.add(locationevaluation_request);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.putExtra("regUserID", regUserID);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //즐겨찾기
                final String location = locationText.getText().toString();
                final String address = addressText.getText().toString();
                Response.Listener<String> respon2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
                                builder.setMessage("즐겨찾기 등록이 되었습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
                                builder.setMessage("즐겨찾기 등록을 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                locationevaluation_Request2 locationevaluation_request2 = new locationevaluation_Request2(regUserID, userID, address, location, respon2);
                RequestQueue requestQueue = Volley.newRequestQueue(locationevaluation.this);
                requestQueue.add(locationevaluation_request2);
            }
        });
        Button button4 = (Button) findViewById(R.id.commentbutton2);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask3().execute();
            }
        });
        //즐겨찾기 해제
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = addressText.getText().toString();
                Response.Listener<String> respon2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
                                builder.setMessage("즐겨찾기 해제가 되었습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
                                builder.setMessage("즐겨찾기 해제 실패 하셨습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                locationevaluation_Request3 locationevaluation_request3 = new locationevaluation_Request3(regUserID,address,respon2);
                RequestQueue requestQueue2 = Volley.newRequestQueue(locationevaluation.this);
                requestQueue2.add(locationevaluation_request3);
            }
        });
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addressText.getText().toString();
                Response.Listener<String> respon3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String image = jsonObject.getString("image");
                                AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
                                builder.setMessage("해당되는 사진이 있습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                                locationImage.setImageBitmap(Base64ToBitmap(image));
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(locationevaluation.this);
                                builder.setMessage("해당되는 사진이 없습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                locationevaluation_Request4 request4 = new locationevaluation_Request4(name,respon3);
                RequestQueue requestQueue3 = Volley.newRequestQueue(locationevaluation.this);
                requestQueue3.add(request4);
            }
        });
    }
    class BackgroundTask3 extends AsyncTask<Void, Void,String> { //리스트 접속
        String target;
        Intent intent = getIntent();
        String address = addressText.getText().toString();
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
            Intent intent4 = new Intent(locationevaluation.this,commentView.class);
            intent4.putExtra("address",address);
            intent4.putExtra("regUserID",regUserID);
            intent4.putExtra("userID",userID);
            intent4.putExtra("commentList",result);
            locationevaluation.this.startActivity(intent4);
        }

    }
    public Bitmap Base64ToBitmap(String base64){
        byte[] bImage = Base64.decode(base64, Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(bImage);
        Bitmap bitmap = BitmapFactory.decodeStream(bais);
        return bitmap;
    }

}
