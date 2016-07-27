package org.example.filehandling;

import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Date;

public abstract class Animal {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String name;
    private String color;
    private Date birthday;
    private float weight;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getBirthdayAsString() {
        return sdf.format(birthday);
    }
    public void setBirthday(String birthday) {
        this.birthday = sdf.parse(birthday, new ParsePosition(0));
    }
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void print() {
        System.out.println("Name: "+name);
        System.out.println("Color: "+color);
        System.out.println("Birthday: "+this.getBirthdayAsString());
        System.out.println("Weight: " +weight);
    }
}