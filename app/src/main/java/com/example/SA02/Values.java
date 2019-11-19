package com.example.SA02;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "value_table")
public class Values {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "value")
    private String mValue;

    public Values(@NonNull String value) {this.mValue = value;}
    public String getValue(){return this.mValue;}
}
