package com.example.project2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class see_notice_Request extends StringRequest {
    final static private String URL = "http://14.49.39.152/TRIP/see_notice.php";
    private Map<String,String> para;
    public see_notice_Request(String noticeTitle, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        para = new HashMap<>();
        para.put("noticeTitle",noticeTitle);

    }
    @Override
    public Map<String,String> getParams(){
        return para;
    }
}
