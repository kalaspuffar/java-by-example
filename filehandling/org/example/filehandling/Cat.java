package org.example.filehandling;

public class Cat extends Animal {
    private int hairballs_per_day;

    public Cat(String name, String birthday, String color, int weight, int hairballs_per_day) {
        this.setName(name);
        this.setBirthday(birthday);
        this.setColor(color);
        this.setWeight(weight);
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