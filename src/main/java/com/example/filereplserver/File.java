package com.example.filereplserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.Collections.max;

public class File {

    private String filename;
    private final int replicationID;
    private int nodeID;
    private int hash;
    private int nodeHash;
    private ArrayList<Integer> smallerKey; // holds nodes with smaller keys than filename
    private ArrayList<Integer> biggerKey;  // holds nodes with bigger keys than filename

    public File(String filename,int nodeHash, ConcurrentHashMap<Integer, String> nodes){
        this.filename = filename;
        this.hash = Hasher.hashCode(filename); //key of file Hashmap
        this.nodeHash = nodeHash;  //this is the key of the owner
        this.replicationID = setNodeID(nodes); //Key of nodes hashMap
    }

    public String getFilename() {
        return filename;
    }

    public int getHash() {
        return hash;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        this.hash = Hasher.hashCode(filename);
    }

    public int setNodeID(ConcurrentHashMap<Integer, String> nodes) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        int temp2 = 0;
        int temp3 = 0;

        for (ConcurrentHashMap.Entry<Integer, String> entry : nodes.entrySet()) {
            int key = entry.getKey();
            //determine node with biggest hash
            if (key > temp2) {
                temp2 = key;
            }
            //determine nodes with hash smaller than file hash
            if (key < hash) {
                array.add(key);
            }
        }
        System.out.println("Temp 2: [biggest node]: "+temp2);
        //Sort array
        Collections.sort(array);
        System.out.println("Array of nodes smaller then filehash: "+ array);
        //return right ID

        if (array.size() == 0)
            return temp2;
        else {
            temp3 = array.get(array.size()-1);
            if (array.size() == 1 && temp3 == nodeHash)
                return temp2;
            else if (temp3 == nodeHash)
                return array.get(array.size()-2);
            else
                return temp3;
        }
    }

    public int getReplicationID() {
        return replicationID;
    }

}