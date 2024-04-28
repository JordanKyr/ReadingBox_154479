package com.example.readingbox_154479.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "books")
public class ListBook {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="books_isbn")
    private String listISBN;

    @ColumnInfo(name="books_title")
    private String listTitle;

    @NonNull
    public String getListISBN() {
        return listISBN;
    }

    public void setListISBN(@NonNull String listISBN) {
        this.listISBN = listISBN;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listBooks) {
        this.listTitle = listTitle;
    }

    public String getListAuthor() {
        return listAuthor;
    }

    public void setListAuthor(String listAuthor) {
        this.listAuthor = listAuthor;
    }

    public int getPubYear() {
        return pubYear;
    }

    public void setPubYear(int pubYear) {
        this.pubYear = pubYear;
    }



    public String getListCover() {
        return listCover;
    }

    public void setListCover(String listCover) {
        this.listCover = listCover;
    }

    @ColumnInfo(name="books_author")
    private String listAuthor;

    @Nullable
    @ColumnInfo(name="books_pubYear")
    private int pubYear;

    @Nullable
    @ColumnInfo(name="books_cover")
    private String listCover;

}
