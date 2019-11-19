package com.example.SA02;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "value_table")
public class Values {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;
    @NonNull
    @ColumnInfo(name = "mileage")
    private Long mMileage;
    @NonNull
    @ColumnInfo(name = "cost")
    private double mCost;
    @NonNull
    @ColumnInfo(name = "amount")
    private double mAmount;

    //Constructor
    public Values(@NonNull int id, @NonNull long mileage, @NonNull double cost, @NonNull double amount) {
        this.mId = id;
        this.mMileage = mileage;
        this.mCost = cost;
        this.mAmount = amount;
    }

    public int getId(){return this.mId;}
    public long getMileage(){return this.mMileage;}
    public double getCost(){return this.mCost;}
    public double getAmount(){return this.mAmount;}
}
