package com.example.project2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Write extends AppCompatActivity {
    ImageView locationImage;
    JSONObject jsonObject;
    Bitmap bitmap;
    private String UploadUrl = "http://14.49.39.152/TRIP/uploadimage.php";
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        spinner = (Spinner) findViewById(R.id.spinner);
        final EditText addressText = (EditText) findViewById(R.id.editText5);
        final EditText evaluationText = (EditText) findViewById(R.id.editText6);
        Button select = (Button) findViewById(R.id.selectimage);
        Button upload = (Button) findViewById(R.id.uploadImage);
        final RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
        final TextView ratingnum = (TextView)findViewById(R.id.ratingnum);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingnum.setText("평점 : "+rating);
            }
        });
        locationImage = (ImageView) findViewById(R.id.locationImage);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationintent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(locationintent, 10);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject2 = new JSONObject(response);
                            String Response = jsonObject2.getString("response");
                            Toast.makeText(Write.this, Response, Toast.LENGTH_LONG).show();
                            locationImage.setImageResource(0);
                            locationImage.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", addressText.getText().toString());
                        params.put("image", imageToString(bitmap));


                        return params;
                    }
                };
                MySingleton.getInstance(Write.this).addToRequestQue(stringRequest);
            }
        });
        Button writebutton = (Button) findViewById(R.id.button6);
        writebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressText.getText().toString();
                String location = spinner.getSelectedItem().toString();
                String evaluation = evaluationText.getText().toString();
                float rating = rb.getRating();
                Response.Listener<String> respon = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent2 = new Intent(Write.this, MainWatch.class);
                                intent2.putExtra("userID", userID);
                                finish();
                            } else {
                                String error = jsonObject.getString("error");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Write.this);
                                builder.setMessage(error)
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Write_Request write_request = new Write_Request(rating,userID, location, evaluation, address, respon);
                RequestQueue request = Volley.newRequestQueue(Write.this);
                request.add(write_request);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                locationImage.setImageBitmap(bitmap);
                locationImage.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


}

