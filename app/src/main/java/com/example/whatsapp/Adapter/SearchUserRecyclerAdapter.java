package com.example.whatsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.ChatDetailActivity;
import com.example.whatsapp.Models.Users;
import com.example.whatsapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchUserRecyclerAdapter extends RecyclerView.Adapter<SearchUserRecyclerAdapter.UserModelViewHolder> {

    private List<Users> userList = new ArrayList<>();
    private Context context;

    public SearchUserRecyclerAdapter(Context context) {
        this.context = context;
    }
    public void updateData(List<Users> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row, parent, false);
        return new UserModelViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserModelViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        ImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull UserModelViewHolder holder, int position) {
        Users model = userList.get(position);
        holder.usernameText.setText(model.getUserName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatDetailActivity.class);
            intent.putExtra("userId", model.getUserID());
            intent.putExtra("userName", model.getUserName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }


}
