package com.example.readingbox_154479.database;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Insert
    public void insertShef(ShelfBooks shelfBooks);
    @Upsert
    public void upsertBook(ListBook listBook);

    @Upsert
    public void upsertUser(ListUser listUser);

    @Delete
    public void deleteListBook(ListBook listBook);

    @Query("SELECT * FROM to_read")
    public List<WantToRead> getToRead();

    @Query("SELECT * "+
            "FROM books B INNER JOIN to_read W ON B.books_isbn=W.tr_isbn "+
            "WHERE W.tr_uid= :id")
public List<ListBook> getBooksToRead(String id);


    @Query("SELECT * "+
            "FROM books B INNER JOIN shelf S ON B.books_isbn=S.shelf_isbn "+
            "WHERE S.shelf_uid= :id")
    public List<ListBook> getShelfBooks(String id);

    @Query("SELECT S.shelf_isbn "+
            "FROM shelf S INNER JOIN books B ON S.shelf_isbn= :isbn " +
            "WHERE S.shelf_uid= :id ")
    public abstract String checkShelf(String isbn, String id);

    @Query("SELECT W.tr_isbn "+
            "FROM to_read W INNER JOIN books B ON W.tr_isbn= :isbn " +
            "WHERE W.tr_uid= :id ")
    public abstract String checktoRead(String isbn, String id);

@Query("SELECT U.users_id "+
        " FROM users U "+
        "WHERE U.users_id= :id " )
    public abstract  String checkUserID(String id);


@Query("SELECT * "+
        "FROM books B "+
        "INNER JOIN shelf S ON B.books_isbn=S.shelf_isbn "+
        "WHERE S.shelf_uid= :id " +
        "UNION  "+
        "SELECT * FROM books B "+
        "INNER JOIN to_read W ON B.books_isbn=W.tr_isbn "+
        "WHERE W.tr_uid= :id")
public abstract  List<ListBook> getAllBooks(String id);

}
