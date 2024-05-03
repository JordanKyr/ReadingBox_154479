package com.example.readingbox_154479.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.units.qual.C;


@Entity(tableName = "saved_quotes",
        primaryKeys = {"quotes_isbn","quotes_uid","quotes_qid"},                        //συνδεση πινακων στον σχεσιακο πινακα με τα κυρια κλειδια τους
        foreignKeys = {                                                               //sindesi 3 pinakon users,books,quotes
                @ForeignKey(entity = ListUser.class,
                        parentColumns = "users_id",
                        childColumns = "quotes_uid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(entity = ListBook.class,
                        parentColumns = "books_isbn",
                        childColumns = "quotes_isbn",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(entity = Quotes.class,
                        parentColumns = "q_id",
                        childColumns = "quotes_qid",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
        })
public class Saved_Quotes {

    @ColumnInfo(name="quotes_isbn") @NonNull
    private String quotesISBN;

    @NonNull
    public String getQuotesISBN() {
        return quotesISBN;
    }

    public void setQuotesISBN(@NonNull String quotesISBN) {
        this.quotesISBN = quotesISBN;
    }

    @NonNull
    public String getQuotesUID() {
        return quotesUID;
    }

    public void setQuotesUID(@NonNull String quotesUID) {
        this.quotesUID = quotesUID;
    }

    public int getQuotesQID() {
        return quotesQID;
    }

    public void setQuotesQID(int quotesQID) {
        this.quotesQID = quotesQID;
    }

    @ColumnInfo(name="quotes_uid") @NonNull
    private String quotesUID;

    @ColumnInfo(name="quotes_qid") @NonNull
    private int quotesQID;


}
