package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Wirte_notice_Request extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/notice_write.php";
    private Map<String,String> para;
    public Wirte_notice_Request(String notice, String noticeTitle, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("notice",notice);
        para.put("noticeTitle",noticeTitle);
    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
