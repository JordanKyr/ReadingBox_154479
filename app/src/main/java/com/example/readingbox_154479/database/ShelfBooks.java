package com.example.readingbox_154479.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "shelf",
        primaryKeys = {"shelf_isbn","shelf_uid"},                        //συνδεση πινακων στον σχεσιακο πινακα με τα κυρια κλειδια τους
        foreignKeys = {
                @ForeignKey(entity = ListUser.class,
                        parentColumns = "users_id",
                        childColumns = "shelf_uid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(entity = ListBook.class,
                        parentColumns = "books_isbn",
                        childColumns = "shelf_isbn",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)})
public class ShelfBooks {


    @NonNull
    public String getShelf_ISBN() {
        return shelf_ISBN;
    }

    public void setShelf_ISBN(@NonNull String shelf_ISBN) {
        this.shelf_ISBN = shelf_ISBN;
    }

    @NonNull
    public String getShelf_UserID() {
        return shelf_UserID;
    }

    public void setShelf_UserID(@NonNull String shelf_UserID) {
        this.shelf_UserID = shelf_UserID;
    }

    @ColumnInfo(name="shelf_isbn") @NonNull
    private String shelf_ISBN;

    @ColumnInfo(name="shelf_uid") @NonNull
    private String shelf_UserID;















}
