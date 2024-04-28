package com.example.readingbox_154479.database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "to_read",
primaryKeys = {"tr_isbn","tr_uid"},                        //συνδεση πινακων στον σχεσιακο πινακα με τα κυρια κλειδια τους
foreignKeys = {
        @ForeignKey(entity = ListUser.class,
                parentColumns = "users_id",
                childColumns = "tr_uid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),

        @ForeignKey(entity = ListBook.class,
                parentColumns = "books_isbn",
                childColumns = "tr_isbn",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})


public class WantToRead {

    @ColumnInfo(name="tr_isbn") @NonNull
private String tr_ISBN;

    @NonNull
    public String getTr_ISBN() {
        return tr_ISBN;
    }

    public void setTr_ISBN(@NonNull String tr_ISBN) {
        this.tr_ISBN = tr_ISBN;
    }

    @NonNull
    public String getTr_UserID() {
        return tr_UserID;
    }

    public void setTr_UserID(@NonNull String tr_UserID) {
        this.tr_UserID = tr_UserID;
    }

    @ColumnInfo(name="tr_uid") @NonNull
    private String tr_UserID;


}
