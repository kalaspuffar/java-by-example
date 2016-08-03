package org.example.filehandling;

import java.util.Map;

public class Cat extends Animal {
    private int hairballs_per_day;

    public Cat(Map<String,String> dataRow) {
        super(dataRow);
        if(!dataRow.containsKey("hairballs_per_day")) return;
        this.hairballs_per_day = Integer.parseInt(dataRow.get("hairballs_per_day"));
    }

    public Cat(String name, String birthday, String color, float weight, int hairballs_per_day) {
        super(name, birthday, color, weight);
        this.hairballs_per_day = hairballs_per_day;
    }

    public int getHairballsPerDay() {
        return hairballs_per_day;
    }
    public void setHairballsPerDay() {
        this.hairballs_per_day = hairballs_per_day;
    }

    public void print() {
        super.print();
        System.out.println("Number of hairballs per day: "+hairballs_per_day);
    }
}