package com.himedia.androidshoppingmall.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.himedia.androidshoppingmall.Data.Constants;
import com.himedia.androidshoppingmall.Data.ProductBean;
import com.himedia.androidshoppingmall.MainActivity;
import com.himedia.androidshoppingmall.R;
import com.himedia.androidshoppingmall.Recycler.ProductAdapter;
import com.himedia.androidshoppingmall.Recycler.ProductItemClickListener;
import com.himedia.androidshoppingmall.Recycler.SelectItemClickListener;
import com.himedia.androidshoppingmall.Recycler.SelectRecyclerAdapter;
import com.himedia.androidshoppingmall.Recycler.ShopItemClickListener;
import com.himedia.androidshoppingmall.Recycler.ShopRecyclerAdapter;
import com.himedia.androidshoppingmall.Request.ProductDetailRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ShopFragment  extends Fragment {
    private Bundle bundle;
    private View view;
    private RecyclerView recyclerView;
    private SelectRecyclerAdapter tAdapter;
    private String[] tData;
    private ShopRecyclerAdapter shopRecyclerAdapter;
    private ArrayList<String[]> pData;
    private SelectItemClickListener selectListener;
    private ShopItemClickListener listener;

    private ProductAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle("data");
        } else {
            bundle = getArguments();
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_shop_fragment, container, false);

        showCategorySelecter();
        showShop();     //　디자인 1
    //    showShop2();  // 디자인 2

        return view;
    }

    private void showCategorySelecter() {
        tData = getContext().getResources().getStringArray(R.array.category);   // 카테고리명

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView = view.findViewById(R.id.categorySelectRecycler);
        recyclerView.setLayoutManager(layoutManager);
        tAdapter = new SelectRecyclerAdapter(tData,selectListener);
        recyclerView.setAdapter(tAdapter);

        // 카테고리 클릭이벤트 수정 시작
        tAdapter.setOnCategoryClickListener(new SelectItemClickListener() {
            @Override
            public void onSelectItemClick(SelectRecyclerAdapter.SelectViewHolder holder, View view, int position) {
                String category = String.valueOf(((TextView)(view.findViewById(R.id.categorySelectTv))).getText());
          //      Toast.makeText(getContext(), "카테고리 :" + category , Toast.LENGTH_SHORT).show();
                showProductByCategory(category);
            }
        });
        // 카테고리 클릭 이벤트 수정 끝
    }

    private void showShop() {
        Bundle args = getArguments();
        pData = (ArrayList<String[]>) args.getSerializable("data");

        String imgUrlPath = Constants.IMAGES_URL;
        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        shopRecyclerAdapter = new ShopRecyclerAdapter();

        for (int i = 0; i < pData.size(); i++) {
            String[] row = pData.get(i);
            String imgUrl  = imgUrlPath + row[0] + "/" + row[3];
            shopRecyclerAdapter.addItem(new ProductBean(row[0],row[1],row[2],imgUrl));
        }

        recyclerView.setAdapter(shopRecyclerAdapter);

        shopRecyclerAdapter.setOnItemClickListener(new ShopItemClickListener() {
            @Override
            public void onItemClick(ShopRecyclerAdapter.ViewHolder holder, View view, int position) {
                ProductBean item = shopRecyclerAdapter.getItem(position);
          //      Toast.makeText(getContext(), "선택된 제품 : " + item.getGoods_title(), Toast.LENGTH_SHORT).show();
                String goods_id = String.valueOf (item.getGoods_id());

                showProductDetail(goods_id); /* 상품상세페이지 */
            }
        });

    }

    private void showProductByCategory(String category) {
        pData.clear();
  //      pData = dbHelper.getProductbyType(type);
  //      shopRecyclerAdapter.updateData(pData);

        // Get the MainActivity instance
        MainActivity activity = (MainActivity) getActivity();
        // Call the doSomething() method
        activity.showShopProduct(category);  // 쇼핑 홈
    }

    private void showShop2() {
        Bundle args = getArguments();
        pData = (ArrayList<String[]>) args.getSerializable("data");

        String imgUrlPath = Constants.IMAGES_URL;
        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter();

        for (int i = 0; i < pData.size(); i++) {
            String[] row = pData.get(i);
            String imgUrl  = imgUrlPath + row[0] + "/" + row[3];
            productAdapter.addItem(new ProductBean(row[0],row[1],row[2],imgUrl));
        }

        recyclerView.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                ProductBean item = productAdapter.getItem(position);
                //    Toast.makeText(getContext(), "선택된 제품 : " + item.getGoods_title(), Toast.LENGTH_SHORT).show();
                String goods_id = String.valueOf (item.getGoods_id());

                showProductDetail(goods_id); /* 상품상세페이지 */
            }
        });
    }

    /* 상품상세페이지 */

    public void showProductDetail(String goods_id) {
        Bundle bundle = new Bundle();
        ArrayList<String[]> data = new ArrayList<>();
        //EditText에 현재 입력되어 있는 값을 가져온다.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // activity/fragment -> fragment 값 전달
                try {
                    JSONArray jsonArray = new JSONArray(response);   // 여러개의 RECORDS 가져옴

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String result = jsonObject.getString("success");  // true/false

                        if ( result == "true"  ) {
                            String goods_id = jsonObject.getString("goods_id");       // 상품코드
                            String goods_title = jsonObject.getString("goods_title");    // 상품명
                            String goods_price = jsonObject.getString("goods_price");  // 상품가격

                            // 숫자에 콤마 추가
                            String goods_price_comma = String.format(Locale.US, "%,d", Integer.parseInt(goods_price));

                            String fileName = jsonObject.getString("fileName");        // 상품이미지명

                            if (fileName != null) {
                                String[] row = new String[4];
                                row[0] = goods_id;
                                row[1] = goods_title;
                                row[2] = goods_price_comma;
                                row[3] = fileName;
                                data.add(row);
                            }

                        }
                    }
                    //   Toast.makeText(getApplicationContext(), "실서버 접속에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    bundle.putStringArrayList("data", (ArrayList) data);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                    productDetailsFragment.setArguments(bundle);
                    transaction.replace(R.id.frameLayout, productDetailsFragment).commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ProductDetailRequest productDetailRequest  = new ProductDetailRequest(goods_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(productDetailRequest);
    }
}
