package com.matthelium.birthdays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matthelium.birthdays.db.MyDbManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.UserViewHolder> {
    private Context context;
    private List<User> users;

    public MyAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = users.get(position);

        holder.nameTextView.setText(user.name);
        holder.birthdayTextView.setText(user.textDate());

        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setOpened(!user.isOpened());
                notifyDataSetChanged();
            }
        });

        if (user.isOpened()) {
            holder.presentsRecyclerView.setVisibility(View.VISIBLE);
            List<String> myPresents = new ArrayList<>();
            String presentsString = users.get(position).presents;
            if (presentsString != null) {
                myPresents.addAll(Arrays.asList(presentsString.split(";\\s*")));
            }
            MyPresentsAdapter presentsAdapter = new MyPresentsAdapter(context, myPresents);
            holder.presentsRecyclerView.setAdapter(presentsAdapter);
        } else {
            holder.presentsRecyclerView.setVisibility(View.GONE);
        }

        holder.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != RecyclerView.NO_POSITION) {
                    int idToDelete = user.id; // Получите ID элемента из списка пользователей
                    MyDbManager myDbManager = new MyDbManager(context);
                    myDbManager.openDb();
                    myDbManager.deleteRow(idToDelete); // Удалить запись из базы данных по ID
                    myDbManager.closeDb();
                    users.remove(user); // Удалить элемент из списка
                    notifyItemRemoved(position); // Обновить RecyclerView
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView birthdayTextView;
        public Button downButton;
        public Button settingsButton;
        public RecyclerView presentsRecyclerView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            birthdayTextView = itemView.findViewById(R.id.birthday);
            downButton = itemView.findViewById(R.id.downBut);
            settingsButton = itemView.findViewById(R.id.setBut);
            presentsRecyclerView = itemView.findViewById(R.id.presentsListView);
            presentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        }
    }
}

