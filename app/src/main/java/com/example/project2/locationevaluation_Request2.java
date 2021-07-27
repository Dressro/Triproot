package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class locationevaluation_Request2 extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/reg.php";
    private Map<String,String> para;
    public locationevaluation_Request2(String regUserID,String userID, String address,String location, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("regUserID",regUserID);
        para.put("userID",userID);
        para.put("address",address);
        para.put("location",location);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
