package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class locationevaluation_Request4 extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/imageget.php ";
    private Map<String,String> para;
    public locationevaluation_Request4(String name, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("name",name);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
