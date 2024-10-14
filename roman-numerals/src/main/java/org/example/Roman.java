package org.example;

import java.util.NavigableMap;
import java.util.TreeMap;

public class Roman {

    NavigableMap<Integer, String> orderedMap = new TreeMap<>();


    public Roman(){
        orderedMap.put(1, "I");
        orderedMap.put(4, "IV");
        orderedMap.put(5, "V");
        orderedMap.put(10, "X");
        orderedMap.put(40, "XL");
        orderedMap.put(50, "L");
        orderedMap.put(100, "C");
        orderedMap.put(500,  "D");
        orderedMap.put(1000, "M");


    }

    public String convert(int value) {
        Integer temp = value;

        StringBuilder res = new StringBuilder();
        while(temp > 0) {
            // Récupérer la plus grande clé inférieure ou égale à l'indice
            Integer key = orderedMap.floorKey(temp);
            res.append(orderedMap.get(key));
            temp-=key;
        }

        return res.toString();
    }

}
