package com.example.filereplserver;

public class Node {
    private String nodeName;
    private int ID;
    private String IP;

    public Node(String nodeName,String IP){
        this.nodeName = nodeName; //nodeName is purely used to make the ID and has no further use.
        this.ID = Hasher.hashCode(nodeName);
        this.IP = IP;
    }

    public String getNodeName(){
        return nodeName;
    }

    public int getID(){
        return ID;
    }

    public String getIP(){
        return IP;
    }

    public void setNodeName(String nodeName){
        this.nodeName = nodeName;
        this.ID = Hasher.hashCode(nodeName);
    }

    public void setIP(String IP){
        this.IP = IP;
    }
}