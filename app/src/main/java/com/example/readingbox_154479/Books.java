package com.example.readingbox_154479;

import java.util.List;

public class Books {
    public Books(String author, String title, String cover, int pubYear, List<String> genre) {
        Author = author;
        Title = title;
        Cover = cover;
        PubYear = pubYear;
        Genre = genre;
    }

    public Books(){}

    private String Author;
    private String Title;

    public List<String> getGenre() {
        return Genre;
    }

    public void setGenre(List<String> genre) {
        Genre = genre;
    }

    private String Cover;
    private int PubYear;

    private List<String> Genre;
    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }



    public void setTitle(String title) {
        Title = title;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public int getPubYear() {
        return PubYear;
    }

    public void setPubYear(int pubYear) {
        PubYear = pubYear;
    }
}
