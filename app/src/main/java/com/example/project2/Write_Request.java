package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Write_Request extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/write.php";
    private Map<String,String> para;
    public Write_Request(float rating,String userID,String location, String evaluation, String address ,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("rating",String.valueOf(rating));
        para.put("address",address);
        para.put("userID",userID);
        para.put("location",location);
        para.put("evaluation",evaluation);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
