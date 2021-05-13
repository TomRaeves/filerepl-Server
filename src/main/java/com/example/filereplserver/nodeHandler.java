package com.example.filereplserver;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.stream.*;
import java.io.*;

public class nodeHandler {

    public static ConcurrentHashMap<Integer, String> nodesMap = new ConcurrentHashMap<Integer, String>();

    public static void updateXML() { //The XML has to be updated after every adding/removing of a node
        System.out.println("Updating XML file");
        try {
            XML.save(nodesMap, "nodeMap.xml");
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static void addNode(String name, String IP) {
        System.out.println("Node added with credentials (name|ip) ==> ("+name+" | "+IP+")");
        Node tempNode = new Node(name, IP);
        System.out.println("The ID of the new node is: "+tempNode.getID());
        nodesMap.put(tempNode.getID(), tempNode.getIP());
        updateXML();
    }

    public static void removeNode(String IP) {
        System.out.println("Removing Node with IP: "+IP+" from hashmap");
        for (Map.Entry<Integer, String> entry : nodesMap.entrySet()) {
            if (IP.equals(entry.getValue())) {
                nodesMap.remove(entry.getKey());
            }
        }
        updateXML();
    }

    public static boolean checkNode(String IP){
        System.out.println("\nChecking if "+IP+" is already part of the active users...");
        Iterator<Map.Entry<Integer, String> > iterator = nodesMap.entrySet().iterator();
        boolean isPresent = false;
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (IP.equals(entry.getValue())) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    public static String getKey(String IP){
        int key = 0;
        Iterator<Map.Entry<Integer, String> > iterator = nodesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (IP.equals(entry.getValue())) {
                key = entry.getKey();
            }
        }
        if (key == 0)
            return "Something went wrong at getKey in nodeHandler";
        else
            return String.valueOf(key);
    }

    public static String getPrevious(String key) {
        int temp = -2147483647;
        int check = Integer.parseInt(key);
        Iterator<Map.Entry<Integer, String> > iterator = nodesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (check > entry.getKey())
                if (temp < entry.getKey())
                    temp = entry.getKey();
        }
        if (temp == -2147483647){
            temp = nodeHandler.getBiggestKey();
        }
        return String.valueOf(temp);
    }

    private static int getBiggestKey() { //because in a map you can't just do max(nodesMap) you have to iterate over all the keys
        int temp = -2147483647;
        Iterator<Map.Entry<Integer, String>> iterator = nodesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (entry.getKey() > temp)
                temp = entry.getKey();
        }
        return temp;
    }

    public static String getNext(String key) {
        int temp = 2147483647;
        int check = Integer.parseInt(key);
        Iterator<Map.Entry<Integer, String> > iterator = nodesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (check < entry.getKey())
                if (temp > entry.getKey())
                    temp = entry.getKey();
        }
        if (temp == 2147483647)
            temp = nodeHandler.getSmallestKey();
        return String.valueOf(temp);
    }

    private static int getSmallestKey() {
        int temp = 2147483647;
        Iterator<Map.Entry<Integer, String>> iterator = nodesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (entry.getKey() < temp)
                temp = entry.getKey();
        }
        return temp;
    }

}