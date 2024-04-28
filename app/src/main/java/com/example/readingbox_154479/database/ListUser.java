package com.example.readingbox_154479.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "users")            //δηλωνω τον πινακα
public class ListUser {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="users_id")
    private String listUserID;      //κλειδι

    public String getListUserID() {
        return listUserID;
    }

    public void setListUserID(String listUserID) {
        this.listUserID = listUserID;
    }

    @ColumnInfo(name="users_name")          //στηλη
    private String listUsername;

    @ColumnInfo(name="users_password")          //στηλη
    private String listPassword;


    public String getListUsername() {
        return listUsername;
    }

    public void setListUsername(String listUsername) {
        this.listUsername = listUsername;
    }

    public String getListPassword() {
        return listPassword;
    }

    public void setListPassword(String listPassword) {
        this.listPassword = listPassword;
    }


}
