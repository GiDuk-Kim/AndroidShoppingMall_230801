package com.himedia.androidshoppingmall.Recycler;

import android.view.View;

public interface ItemClickListener extends ProductItemClickListener {
    @Override
    void onItemClick(ProductAdapter.ViewHolder holder, View view, int position);
}
