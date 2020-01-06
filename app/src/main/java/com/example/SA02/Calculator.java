package com.example.SA02;

public class Calculator {
    public Calculator() {
    }

    public double LitresToGallons(double litres){
        double con = litres/4.546;
        return con;
    }

    public double MilesToKilometres(int miles){
        double con = (double)miles*1.609;
        return con;
    }

    public double CPM(double cost, int miles){
        double con = cost/(double)miles;
        return con;
    }

    public double CPK(double cost, int miles){
        double con = MilesToKilometres(miles);
        double con1 = cost/con;
        return con1;
    }

    public double CPG(double cost, double litres){
        double con = LitresToGallons(litres);
        double con1 = cost/con;
        return con1;
    }

    public double CPL(double cost, double litres){
        double con = cost/litres;
        return con;
    }

    public double MPG(int miles, double litres){
        double con = LitresToGallons(litres);
        double con1 = (double)miles/con;
        return con1;
    }

    public double MPL(int miles, double litres){
        double con = (double)miles/litres;
        return con;
    }

    public double KPG(int miles, double litres){
        double con = MilesToKilometres(miles);
        double con1 = LitresToGallons(litres);
        double con2 = con/con1;
        return con2;
    }

    public double KPL(int miles, double litres){
        double con = MilesToKilometres(miles);
        double con1 = con/litres;
        return con1;
    }
}
