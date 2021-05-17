package com.example.filereplserver;

import java.lang.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class fileHandler {

    public static ConcurrentHashMap<Integer, File> filesMap = new ConcurrentHashMap<>();

    public static void addFile(String filename, int nodeHash ,ConcurrentHashMap<Integer, String> nodesMap){
        System.out.println("Adding the following file to the files hashmap: " +filename);
        File temp = new File(filename, nodeHash, nodesMap);
        filesMap.put(temp.getHash(),temp);
        System.out.println("File added to node with nodeID: "+temp.getHash()+" with filename: "+filename);
    }

    public static String getIP (String name,ConcurrentHashMap<Integer, String> nodes){
        int id = -1;
        for (Map.Entry<Integer, File> entry : filesMap.entrySet()) {
            if (name.equals(entry.getValue().getFilename())) {
                id = entry.getValue().getReplicationID();
            }
        }
        if (id == -1){
            return "Something went wrong at getIP in fileHandler";
        }else
            return nodes.get(id);
    }

    public static String searchFile(ConcurrentHashMap<Integer, File> filesMap,String fileName){
        int hash=Hasher.hashCode(fileName);

        for (ConcurrentHashMap.Entry<Integer, File> entry : filesMap.entrySet()) {
            if(hash==entry.getKey()){
                return "Filename with ID "+entry.getKey()+" is located at node with ID "+ entry.getValue().getNodeID();
            }
        }
        return "could not find the file you were looking for.";
    }

    public static String getHash(String name){
        int hash = -1;
        for (Map.Entry<Integer, File> entry : filesMap.entrySet()) {
            if (name.equals(entry.getValue().getFilename())) {
                hash = entry.getValue().getHash();
            }
        }
        if (hash == -1)
            return "Something went wrong at getHash in fileHandler";
        else
            return String.valueOf(hash);
    }

    public static int getReplicationID(String name){
        int id = -1;
        for (Map.Entry<Integer, File> entry : filesMap.entrySet()) {
            if (name.equals(entry.getValue().getFilename())) {
                id = entry.getValue().getReplicationID();
            }
        }
        return id;
    }

    public static fileHandler instance;
    public static fileHandler getInstance()
    {
        if(instance==null)
            instance = new fileHandler();
        return instance;
    }
}