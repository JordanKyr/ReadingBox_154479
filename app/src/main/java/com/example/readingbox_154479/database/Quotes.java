package com.example.readingbox_154479.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quotes")
public class Quotes {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="q_id")
    private int quote_id;

    @ColumnInfo(name="quote")
    private String quote;


    @NonNull
    public int getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(@NonNull int quote_id) {
        this.quote_id = quote_id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Quotes(String quote){
        this.quote=quote;
    }

}
