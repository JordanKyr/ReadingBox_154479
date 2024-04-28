package com.example.readingbox_154479.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface RB_DAO {
                                    //to Data Access Object βοηθαει στην αλληλεπιδραση με τα αποθηκευμενα στοιχεια της βασης
    @Insert
    public void addUser(ListUser listUser);


    @Insert
    public void insertWantRead(WantToRead wantToRead);

    @Upsert
    public void upsertBook(ListBook listBook);

    @Upsert
    public void upsertUser(ListUser listUser);



    @Query("SELECT * FROM to_read")
    public List<WantToRead> getToRead();
}
