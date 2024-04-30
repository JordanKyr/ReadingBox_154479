package com.example.readingbox_154479.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;



@Database(entities = {ListUser.class, ListBook.class, WantToRead.class, ShelfBooks.class},version =1)
public abstract class RB_DB extends RoomDatabase {
    public abstract RB_DAO rbDao();

}
