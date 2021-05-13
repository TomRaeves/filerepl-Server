package com.example.filereplserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {

    @PutMapping("/addNode/{name}")
    public String addNode(@PathVariable("name")String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String clientAddr = request.getRemoteAddr();
        if(ExistTest(clientAddr)){
            return "You are already added, You cannot add yourself multiple times.\n";
        }
        nodeHandler.addNode(name,clientAddr);
        return "Node added succesfully!\n";
    }

    @PutMapping("/removeNode")
    public String removeNode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String clientAddr = request.getRemoteAddr();
        if(!ExistTest(clientAddr)){
            return "You are not yet added to the system!\nTo add yourself as a user use the following command: /addNode/<name>\n";
        }
        nodeHandler.removeNode(clientAddr);
        return "User succesfully deleted\n";
    }

    @GetMapping("/getID")
    public int getID(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String clientAddr = request.getRemoteAddr();
        return Integer.parseInt(nodeHandler.getKey(clientAddr));
    }

    @GetMapping("/searchFile/{name}")
    public String searchFile(@PathVariable("name")String name){
        return fileHandler.searchFile(fileHandler.filesMap,name);
    }

    @PutMapping("/addFile/{filename}")
    public String addFile(@PathVariable("filename") String fileName){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if(!ExistTest(request.getRemoteAddr())){
            return "You are not yet added to the system therefore you cannot add files!\nTo add yourself as a user use the following command: /addNode/<name>\n";
        }
        fileHandler.addFile(fileName, nodeHandler.nodesMap);
        Status.showStatus();
        return "The following file has been added succesfully " +fileName+" with fileID: " + Hasher.hashCode(fileName)+ " \n" ;
    }

    private boolean ExistTest(String ip) {
        boolean testPassed = false;
        if(nodeHandler.checkNode(ip))  //returns true when already in hashMap
            testPassed = true;
        return testPassed;
    }
}