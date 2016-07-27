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
        List<Map<String,String>> animalData = new ArrayList<Map<String,String>>();

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
                animalData.add(dataRow);
            }

            for(Map<String,String> dataRow : animalData) {
                for(String key : dataRow.keySet()) {
                    System.out.println(key+"="+dataRow.get(key));
                }
                System.out.println("----------------------------");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}