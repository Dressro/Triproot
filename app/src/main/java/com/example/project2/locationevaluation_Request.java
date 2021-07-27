package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class locationevaluation_Request extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/evl.php";
    private Map<String,String> para;
    public locationevaluation_Request(String userID, String address, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("userID",userID);
        para.put("address",address);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
