package com.example.readingbox_154479.database;
import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface RB_DAO {
                                    //to Data Access Object βοηθαει στην αλληλεπιδραση με τα αποθηκευμενα στοιχεια της βασης
    @Insert
    public void addUser(ListUser listUser);
}
