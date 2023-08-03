package com.himedia.androidshoppingmall.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.himedia.androidshoppingmall.R;

public class SelectRecyclerAdapter extends RecyclerView.Adapter<SelectRecyclerAdapter.SelectViewHolder> {
    private String[] data;
    private SelectItemClickListener listener;

    public SelectRecyclerAdapter(String[] data, SelectItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_select_type_card, viewGroup, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int i) {
        selectViewHolder.categorySelect.setText(data[i]);

        final int index = i;
        selectViewHolder.categorySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.length;
    }

    public class SelectViewHolder extends RecyclerView.ViewHolder{
        TextView categorySelect;

        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);

            categorySelect = itemView.findViewById(R.id.categorySelectTv);
        }
    }
}
