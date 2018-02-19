package com.mibrh.firechat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<Message> messageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, text;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username_display);
            text = (TextView) view.findViewById(R.id.text_display);
        }
    }


    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.username.setText(message.getUsername());
        holder.text.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}