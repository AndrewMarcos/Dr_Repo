package com.example.andrewsamir.drromance.Data;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Created by Andrew Samir on 6/28/2016.
 */
public class data_delivery {

    String person_id,time,code,mobile,name,phone,oreder_id;
    Firebase firebase;
    DataSnapshot images;
    DataSnapshot messages,signIn;


    public data_delivery(String person_id, DataSnapshot images, DataSnapshot messages, String oreder_id, String name, String code, String time) {
        this.person_id = person_id;
        this.images = images;
        this.messages = messages;
        this.oreder_id = oreder_id;
        this.name = name;
        this.code = code;
        this.time = time;
    }

    public String getPerson_id() {
        return person_id;
    }

    public String getOreder_id() {
        return oreder_id;
    }

    public DataSnapshot getImages() {
        return images;
    }

    public DataSnapshot getMessages() {
        return messages;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
