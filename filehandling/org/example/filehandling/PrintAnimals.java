package org.example.filehandling;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Todo:
 * - Create cats and dogs representation
 * - Read file from disk
 * - Parse data and create a list
 * - (Maybe)Sort list depending on arguments
 */
public class PrintAnimals {
    public static void main(String[] args) {
        List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();

        try {
            BufferedReader in = new BufferedReader(new FileReader("animals.csv"));

            String line = in.readLine();

            String[] headerArray = line.split(",");

            while((line = in.readLine()) != null) {
                Map<String,String> dataRow = new HashMap<String, String>();
                String[] dataArray = line.split(",");

                for(int i=0; i<headerArray.length; i++) {
                    if(dataArray.length <= i) continue;
                    if(dataArray[i].isEmpty()) continue;

                    dataRow.put(headerArray[i], dataArray[i]);
                }
                dataList.add(dataRow);
            }

            List<Animal> animalList = new ArrayList<Animal>();
            for(Map<String,String> dataRow : dataList) {
                if(!dataRow.containsKey("animal_type")) continue;
                Animal animal = null;
                if("dog".compareTo(dataRow.get("animal_type")) == 0) {
                    animal = new Dog(dataRow);
                }
                if("cat".compareTo(dataRow.get("animal_type")) == 0) {
                    animal = new Cat(dataRow);
                }
                animalList.add(animal);
            }

            for(Animal animal : animalList) {
                animal.print();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}