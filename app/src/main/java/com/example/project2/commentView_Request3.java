package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class commentView_Request3 extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/comments.php";
    private Map<String,String> para;
    public commentView_Request3(String register, String comments, String addresss, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("comments",comments);
        para.put("register",register);
        para.put("address",addresss);

    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
