package com.stjohns.stormchat.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stjohns.stormchat.R;

import java.util.List;

/**
 * Created by mbill740 on 12/10/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<Message> list;

    public UserAdapter(List<Message> list) {
        this.list = list;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message, parent, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Message message = list.get(position);

        holder.userName.setText(message.getUser());
        holder.messageText.setText(message.getContent()); // content has private access
        holder.date.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView messageText, date, userName;

        public UserViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            userName = itemView.findViewById(R.id.userName);
            date = itemView.findViewById(R.id.date);
        }
    }
}


