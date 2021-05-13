package com.example.filereplserver;

import java.io.IOException;
import java.net.*;

public class UDPServer extends Thread {

    private static boolean running = true;
    private static DatagramSocket receiveSocket;
    private static int receivePort;
    private static int sendPort;

    public UDPServer(int receivePort, int sendPort) {
        System.out.println("Starting UDP server...");
        UDPServer.receivePort = receivePort;
        UDPServer.sendPort = sendPort;
        try {
            UDPServer.receiveSocket = new DatagramSocket(UDPServer.receivePort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[255];
        while (UDPServer.running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                UDPServer.receiveSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            onDataReceived(packet);
        }
    }

    public void shutdown() {
        if (UDPServer.receiveSocket.isClosed())
            UDPServer.running = false;
        else{
            UDPServer.receiveSocket.close();
            UDPServer.running = false;
        }
    }

    private void onDataReceived(DatagramPacket packet) {
        InetAddress address = packet.getAddress();
        String data = new String(packet.getData(), 0, packet.getLength());
        System.out.println("UNI[" + address + "]UDP packet received: " + data);
        handleData(data);
    }

    private void handleData(String data) {
        //VB message:Exit,Curr: 654654654,Prev: 65456872,Next: 2635486454
        int index = data.indexOf(",");
        String command = null;
        String message = null;
        if (index != -1) {
            command = data.substring(0, index);
            message = data.substring(index + 1);
        }
        assert command != null;
        switch (command) {
            case "Exit":
                String current;
                String previous;
                String next;
                index = (message.indexOf("Curr: ")) + 6;
                int indexx = message.indexOf(",Prev: ");
                current = message.substring(index, indexx);
                indexx = indexx + 7;
                index = message.indexOf(",Next: ");
                previous = message.substring(indexx, index);
                index = index + 7;
                next = message.substring(index);
                String previousIP = nodeHandler.nodesMap.get(Integer.parseInt(previous));
                previousIP = previousIP.substring(1);
                previousIP = "1"+previousIP;
                String nextIP = nodeHandler.nodesMap.get(Integer.parseInt(next));
                nextIP = nextIP.substring(1);
                nextIP = "1"+nextIP;
                System.out.println("NextIP = "+nextIP);
                System.out.println("previousIP = "+previousIP);
                if (nodeHandler.nodesMap.size() == 1)
                    System.out.println("Last node left the network...");
                else{
                    try {
                        sendUniCast("NewNext," + next + "," + current, InetAddress.getByName(previousIP));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    try {
                        sendUniCast("NewPrev," + previous + "," + current, InetAddress.getByName(nextIP));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                nodeHandler.removeNode(nodeHandler.nodesMap.get(Integer.parseInt(current)));
                break;

            default:
                break;
        }
        Status.showStatus();
    }

    private void sendUniCast(String message, InetAddress hostAddress){
        System.out.println("Sending unicast: [" + hostAddress + "]: " + message);
        if (message != null) {
            DatagramSocket UDPSocket = null;
            try {
                UDPSocket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, hostAddress, UDPServer.sendPort);
            try {
                assert UDPSocket != null;
                UDPSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UDPSocket.close();
        }
    }
}