package org.example.filehandling;

import java.util.Map;

public class Dog extends Animal {
    private int walks_per_day;

    public Dog(Map<String,String> dataRow) {
        super(dataRow);
        if(!dataRow.containsKey("walks_per_day")) return;
        this.walks_per_day = Integer.parseInt(dataRow.get("walks_per_day"));
    }

    public Dog(String name, String birthday, String color, int weight, int walks_per_day) {
        super(name, birthday, color, weight);
        this.walks_per_day = walks_per_day;
    }

    public int getWalksPerDay() {
        return walks_per_day;
    }

    public void setWalksPerDay(int walks_per_day) {
        this.walks_per_day = walks_per_day;
    }

    public void print() {
        super.print();
        System.out.println("Number of walks per day: "+walks_per_day);
    }
}