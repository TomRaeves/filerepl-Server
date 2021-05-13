package com.example.filereplserver;

public class Hasher {

    public static int hashCode(String input){
        long max = 2147483647;
        long min = -2147483647;

        long result = ((input.hashCode()+max)*32768)/(max+Math.abs(min));

        //double result = (IP.hashCode() + hostname.hashCode()+max)*(32768d/(max+abs(min)));
        //Eventueel hash maken over naam EN IP zodat je telkens een verschillende ID hebt

        return (int) result;
    }
}