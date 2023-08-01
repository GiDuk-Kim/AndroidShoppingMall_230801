package com.himedia.androidshoppingmall.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.himedia.androidshoppingmall.Data.ProductBean;
import com.himedia.androidshoppingmall.R;

import java.util.ArrayList;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.ViewHolder>
        implements OnCartItemClickListener {
    ArrayList<ProductBean> items = new ArrayList();

    OnCartItemClickListener listener;

    public OnCartItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnCartItemClickListener listener) {
        this.listener = listener;
    }

    TextView textView1,textView2,textView4;
    ImageView imageView1;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.cart_item, viewGroup, false);
      //  View itemView = inflater.inflate(R.layout.product_item, viewGroup, false);

         /*
            장바구니 리스트 안뜬 이유  2023.07.27 오류 해결
            View view = inflater.inflate(R.layout.activity_cart_fragment, container, false);
            CartFragment에 참조하는 activity_cart_fragment.xml -> RecycleView 넣을려고 해서 오류
            cart_item -> RecycleView 넣어야 정상 리스트가 출력된다.
         */

        return new ViewHolder(itemView,  this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ProductBean item = items.get(position);
        viewHolder.setItem(item);
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ProductBean item) {
        items.add(item);
    }

    public void setItems(ArrayList<ProductBean> items) {
        this.items = items;
    }

    public ProductBean getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ProductBean item) {
        items.set(position, item);
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        ImageView imageView1;

        public ViewHolder(View itemView, OnCartItemClickListener listener) {    // final OnProdu ....
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);   // 수량
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);

            imageView1 = itemView.findViewById(R.id.imageView1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(ProductBean item) {
            textView1.setText(String.valueOf(item.getGoods_qty())); // goods_qty    // 230727 오류발생(43~46 오류떄문)
            textView2.setText(item.getGoods_title());                // goods_title
            textView4.setText(String.valueOf(item.getGoods_price()));     // goods_price
            Glide.with(itemView.getContext()).load(item.getImageRes()).into(imageView1);
        }

    }

}
