package com.matthelium.birthdays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyPresentsAdapter extends RecyclerView.Adapter<MyPresentsAdapter.ViewHolder> {
    private Context context;
    private List<String> presents;

    public MyPresentsAdapter(Context context, List<String> presents) {
        this.context = context;
        this.presents = presents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String present = presents.get(position);
        holder.presentTextView.setText(present);
    }

    @Override
    public int getItemCount() {
        return presents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView presentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            presentTextView = itemView.findViewById(R.id.presentName);
        }
    }
}
















