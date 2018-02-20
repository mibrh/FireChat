package com.mibrh.firechat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private String mainuser;
    private List<Message> messageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, text;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username_display);
            text = (TextView) view.findViewById(R.id.text_display);
        }
    }

    // remove username for original
    public MessageAdapter(List<Message> messageList, String username) {
        this.messageList = messageList;
        this.mainuser = username;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case 1: layout = R.layout.row_message_right;    break;
            default: layout = R.layout.row_message_left;   break;
            // TODO:
            // make new layout if isUser
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

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

    @Override
    public int getItemViewType(int position) {
        String user = messageList.get(position).getUsername();
        if (user.equals(this.mainuser)){
            return 1;
        } else {
            return 0;
        }
    }
}