package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login_Request extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/Login.php";
    private Map<String,String> para;
    public Login_Request(String userID, String Password, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("userID",userID);
        para.put("Password",Password);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
