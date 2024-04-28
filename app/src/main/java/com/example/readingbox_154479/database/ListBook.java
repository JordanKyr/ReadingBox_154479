package com.example.readingbox_154479.database;

import androidx.annotation.NonNull;
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
    private String listBooks;

    @NonNull
    public String getListISBN() {
        return listISBN;
    }

    public void setListISBN(@NonNull String listISBN) {
        this.listISBN = listISBN;
    }

    public String getListBooks() {
        return listBooks;
    }

    public void setListBooks(String listBooks) {
        this.listBooks = listBooks;
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

    public List<String> getList_Genre() {
        return list_Genre;
    }

    public void setList_Genre(List<String> list_Genre) {
        this.list_Genre = list_Genre;
    }

    public String getListCover() {
        return listCover;
    }

    public void setListCover(String listCover) {
        this.listCover = listCover;
    }

    @ColumnInfo(name="books_author")
    private String listAuthor;

    @ColumnInfo(name="books_pubYear")
    private int pubYear;

    @ColumnInfo(name="books_genre")
    private List<String> list_Genre;

    @ColumnInfo(name="books_cover")
    private String listCover;

}
