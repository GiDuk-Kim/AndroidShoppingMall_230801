package com.himedia.androidshoppingmall.Recycler;

import android.view.View;

public interface CartItemClickListener {
   public void onItemClick(CartRecycleAdapter.ViewHolder viewHolder, View view, int position);
}
