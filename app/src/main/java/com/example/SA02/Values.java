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
    private int mMileage;

    @NonNull
    @ColumnInfo(name = "cost")
    private double mCost;

    @NonNull
    @ColumnInfo(name = "amount")
    private double mAmount;

    @NonNull
    @ColumnInfo(name = "date")
    private String mDate;

    //Constructor
    public Values(@NonNull int mileage, @NonNull double cost, @NonNull double amount, @NonNull String date) {

        this.mMileage = mileage;
        this.mCost = cost;
        this.mAmount = amount;
        this.mDate = date;
    }

    public void setId(int mId){
        this.mId = mId;
    }

    public void setMileage(int mMileage){
        this.mMileage = mMileage;
    }

    public void setmCost(double mCost) {
        this.mCost = mCost;
    }

    public void setmAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public void setmDate(@NonNull String mDate) {
        this.mDate = mDate;
    }

    public int getId(){return this.mId;}
    public int getMileage(){return this.mMileage;}
    public double getCost(){return this.mCost;}
    public double getAmount(){return this.mAmount;}
    public String getDate(){return this.mDate;}
}
