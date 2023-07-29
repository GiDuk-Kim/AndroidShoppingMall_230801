package com.himedia.androidshoppingmall.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.himedia.androidshoppingmall.Data.Constants;   // 전역 상수 정의 By Dean 2023.07.29

import java.util.HashMap;
import java.util.Map;

public class CartListRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    public static final String URL = Constants.WEB_URL + "ListCart.php";
    private Map<String, String> map;

    public CartListRequest(String member_id, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("member_id", member_id);
    }

    @Override
    protected  Map<String, String> getParams() throws AuthFailureError
    {
        return map;
    }
}
