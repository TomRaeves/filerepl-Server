package com.example.filereplserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Scanner;

@SpringBootApplication
public class FilereplserverApplication {

//https://www.baeldung.com/java-broadcast-multicast

	private static final int receivePort = 4501;
	private static final int sendPort = 4500;
	private static final int multicastPort = 3456;
	private static final String multicastAddress = "225.10.10.10";
	private static boolean running = true;

	public static void main(String[] args){
		UDPServer UDP = new UDPServer(receivePort,sendPort);
		UDP.start();
		UDPMultiServer UDPM = new UDPMultiServer(multicastPort,multicastAddress,sendPort);
		UDPM.start();
		System.out.println("starting REST server... To stop the server type in Exit");
		ConfigurableApplicationContext ctx = SpringApplication.run(FilereplserverApplication.class, args);
		Scanner sc = new Scanner(System.in);
		System.out.println("List of all commands: ");
		System.out.println("Stop the server: 'Exit'");
		System.out.println("Show the server's topology: 'Show'");
		System.out.println("Get all commands: 'help'");
		while(running){
			String input = sc.nextLine();
			switch(input) {
				case "Exit" :
					System.out.println("Stopping UDP server...");
					UDP.shutdown();
					System.out.println("Stopping UDPMulti server...");
					UDPM.shutdown();
					System.out.println("Stopping RESTServer...");
					ctx.close();
					System.out.println("Stopping DiscoveryServer...");
					running = false;
					break;

				case "Show" :
					Status.showStatus();
					break;

				case "help" :
					System.out.println("\nList of all commands: ");
					System.out.println("Stop the server: 'Exit'");
					System.out.println("Show the server's topology: 'Show'");
					System.out.println("Get all commands: 'help'\n");
					break;
			}
		}
		System.exit(0);
	}
}

