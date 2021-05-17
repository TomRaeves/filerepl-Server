package com.example.filereplserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{

    //https://stackoverflow.com/questions/50733861/implementing-a-simple-java-tcp-server

    private static int receivePort;
    private static boolean running = true;

    public TCPServer(int receivePort){
        TCPServer.receivePort = receivePort;
        System.out.println("Starting TCP server listening on port "+TCPServer.receivePort);
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(TCPServer.receivePort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (TCPServer.running) {
            if (serverSocket != null) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println(" ");
                    TCPHandler TCPH = new TCPHandler(socket,socket.getInetAddress());
                    TCPH.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}