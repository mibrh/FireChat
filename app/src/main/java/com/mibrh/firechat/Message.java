package com.mibrh.firechat;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class Message {

    String username;
    String text;


    public Message(String username, String text){
        this.username = username;
        this.text = text;
    }

    public static Message deserialize(DataSnapshot snapshot){
        String username = snapshot.child("username").getValue().toString();
        String text = snapshot.child("text").getValue().toString();

        return new Message(username, text);
    }

    public HashMap<String, String> serialize(){
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("username", this.username);
        hashmap.put("text", this.text);
        return hashmap;
    }

    public String toString(){
        return this.username + ": " + this.text;
    }

}
