package com.himedia.androidshoppingmall.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.himedia.androidshoppingmall.Data.CartBean;
import com.himedia.androidshoppingmall.Data.Constants;
import com.himedia.androidshoppingmall.Data.PreferenceManager;
import com.himedia.androidshoppingmall.R;
import com.himedia.androidshoppingmall.Recycler.CartRecycleAdapter;
import com.himedia.androidshoppingmall.Recycler.OnCartItemClickListener;
import com.himedia.androidshoppingmall.Request.CartDelRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class CartFragment extends Fragment {
    private View view;
    private Bundle bundle;

    RecyclerView recyclerView;
    CartRecycleAdapter cartRecycleAdapter;
    private PreferenceManager pManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pManager = new PreferenceManager();  // 로그인후 토큰이 만들어져 자동 로그인 기능

        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle("data");
        } else {
            bundle = getArguments();
        }
    }

    // 메인. 슬라이드 형식 화면 절반치 광고, 아래에 상품 6개 정도 보여주기
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cart_fragment, container, false);

        showCartList();
        return view;
    }

    public void showCartList()
    {
        String member_id = pManager.getString(getContext(), "member_id");
        Bundle args = getArguments();
        ArrayList<String[]> data = (ArrayList<String[]>) args.getSerializable("data");


        String imgUrlPath = Constants.IMAGES_URL;
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        cartRecycleAdapter = new CartRecycleAdapter();

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            String imgUrl = imgUrlPath + row[0] + "/" + row[3];
            cartRecycleAdapter.addItem(new CartBean(row[0], row[1], row[2], imgUrl, row[4], row[5]));   // public CartBean(String goods_id, String goods_title, String goods_price, String imageRes, String goods_qty, cart_id) {
        }

        recyclerView.setAdapter(cartRecycleAdapter);

        cartRecycleAdapter.setListener(new OnCartItemClickListener() {
            @Override
            public void onItemClick(CartRecycleAdapter.ViewHolder viewHolder, View view, int position) {
                CartBean item = cartRecycleAdapter.getItem(position);

                TextView btnDelCart = view.findViewById(R.id.btnDelCart);
                btnDelCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cart_id = item.getCart_id();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //Register.php에서 $response["success"] = true; 값을 받음
                                    boolean success = jsonObject.getBoolean("success");  // true, false 가져옴

                                    if (success) {
                                        Toast.makeText(getContext(), "장바구니를 삭제하였습니다.", Toast.LENGTH_SHORT).show();

                                        // 장바구니 삭제 하고 새로고침  by Dean 20230801
                                        cartRecycleAdapter.removeItem(position);         // 상품 삭제 removeItem() 새로 추가함
                                        cartRecycleAdapter.notifyItemRemoved(position);  // 상품 삭제 됨 알림
                                        cartRecycleAdapter.notifyDataSetChanged();       // 새로고침
                                        // 장바구니 삭제 하고 새로고침  by Dean 20230801

                                    } else {
                                        Toast.makeText(getContext(), "장바구니를 삭제하는 과정에서 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        };

                        CartDelRequest cartDelRequest = new CartDelRequest(cart_id, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(cartDelRequest);
                    }
                });

            }
        });
    }

}
