package com.example.filereplserver;

import java.lang.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class fileHandler {

    public static ConcurrentHashMap<Integer, File> filesMap = new ConcurrentHashMap<>();

    public static void addFile(String filename, ConcurrentHashMap<Integer, String> nodesMap){
        System.out.println("Adding the following file to the files hashmap: " +filename);
        File temp = new File(filename, nodesMap);
        filesMap.put(temp.getHash(),temp);
        System.out.println("File added to node with nodeID: "+temp.getNodeID());
    }

    public static void removeFile(String filename){
        System.out.println("The following file will be removed from the files hashmap: "+filename);
        for (Map.Entry<Integer, File> entry : filesMap.entrySet()){
            if (filename.equals(entry.getValue().getFilename())){
                filesMap.remove(entry.getKey());
            }
        }
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

    public static fileHandler instance;
    public static fileHandler getInstance()
    {
        if(instance==null)
            instance = new fileHandler();
        return instance;
    }
}