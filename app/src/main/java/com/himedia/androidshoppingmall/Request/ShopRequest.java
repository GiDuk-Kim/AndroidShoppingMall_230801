package com.himedia.androidshoppingmall.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.himedia.androidshoppingmall.Data.Constants;

import java.util.HashMap;
import java.util.Map;

public class ShopRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    public static final String URL = Constants.WEB_URL + "ListShop.php";

    private Map<String, String> map;

    public ShopRequest(String category, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("category", category);
    }

    @Override
    protected  Map<String, String> getParams() throws AuthFailureError
    {
        return map;
    }
}
