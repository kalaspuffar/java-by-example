package org.example.filehandling;

public class Dog extends Animal {
    private int walks_per_day;

    public Dog(String name, String birthday, String color, int weight, int walks_per_day) {
        this.setName(name);
        this.setBirthday(birthday);
        this.setColor(color);
        this.setWeight(weight);
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