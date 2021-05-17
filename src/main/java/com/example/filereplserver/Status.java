package com.example.filereplserver;

import java.lang.*;
import java.util.*;

public class Status {

    public static void showStatus() {
        printNodeMap();
        printMap();
        System.out.println("\n");
    }

    public static void printMap(){
        int counter=0;
        Iterator<Map.Entry<Integer, File>> iterator = fileHandler.filesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            counter++;
            Map.Entry<Integer, File> entry = iterator.next();
            System.out.println("File "+counter+" with Name: "+entry.getValue().getFilename()+", ID: "+entry.getKey()+", NodeID: "+fileHandler.searchFile(fileHandler.filesMap,entry.getValue().getFilename()));
        }
    }

    public static void printNodeMap(){
        System.out.println("\nAmount of nodes in the network: " + nodeHandler.nodesMap.size());
        int counter=0;
        Iterator<Map.Entry<Integer, String>> iterator = nodeHandler.nodesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            counter++;
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println("Node "+counter+" with IP: "+entry.getValue()+", || Previous ID: "+nodeHandler.getPrevious(Integer.toString(entry.getKey()))+" || with ID: "+entry.getKey()+"|| Next ID: "+nodeHandler.getNext(Integer.toString(entry.getKey())));
        }
    }
}