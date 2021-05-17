package com.example.filereplserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/getNodesMap")
    public String getMap(){
        System.out.println("Someone requests nodes hashMap ");
        return "Current active users: "+nodeHandler.nodesMap;
    }

}
