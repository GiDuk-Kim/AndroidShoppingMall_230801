package com.himedia.androidshoppingmall.Recycler;

import android.view.View;

public interface ProductItemClickListener {
    public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position);
}
