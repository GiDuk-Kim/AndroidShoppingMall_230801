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

public class ShopRecyclerAdapter extends RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder>
        implements ShopItemClickListener {
    ArrayList<ProductBean> items = new ArrayList();

    ShopItemClickListener listener;
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public ShopRecyclerAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_card, viewGroup, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopRecyclerAdapter.ViewHolder viewHolder, int position) {
        ProductBean item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        if(items == null)
            return 0;
        else
            return items.size();
    }

    public void updateData(ArrayList<ProductBean> data){
        this.items = data;
        notifyDataSetChanged();  // 새로고침 기능
    }

    public ProductBean getItem(int position) {
        return items.get(position);
    }
    public void addItem(ProductBean item) {
        items.add(item);
    }
    public void setOnItemClickListener(ShopItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productNameTv;
        TextView productPriceTv;

        public ViewHolder(View itemView, ShopItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            productNameTv = itemView.findViewById(R.id.productNameTv);
            productPriceTv = itemView.findViewById(R.id.productPriceTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ShopRecyclerAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(ProductBean item) {
            Glide.with(itemView.getContext()).load(item.getImageRes()).into(imageView);
            productNameTv.setText(item.getGoods_title());                // goods_title
            productPriceTv.setText(item.getGoods_price());     // goods_price
        }

    }
}