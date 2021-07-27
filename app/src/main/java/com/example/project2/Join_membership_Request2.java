package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Join_membership_Request2 extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/name.php";
    private Map<String,String> para;
    public Join_membership_Request2(String userID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("userID",userID);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
