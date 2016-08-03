package com.example.andrewsamir.drromance.Data;

/**
 * Created by Andrew Samir on 7/23/2016.
 */
public class All_Customers {

    String name,code,id;

    public All_Customers(String name, String code, String id) {
        this.name = name;
        this.code = code;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }
}
