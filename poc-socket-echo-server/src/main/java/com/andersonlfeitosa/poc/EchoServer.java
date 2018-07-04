package com.andersonlfeitosa.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
  public static void main(String[] args) throws IOException {

    int portNumber = getEchoServerPort();
    
    System.out.printf("ECHO SERVER PARAMS:\nECHO_SERVER_PORT=%1$s\n", portNumber);

    try (ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        System.out.println("server => received: " + inputLine);
        out.println(inputLine);
      }
    } catch (IOException e) {
      System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
      System.out.println(e.getMessage());
    }
  }

  private static int getEchoServerPort() {
    String port = System.getenv("ECHO_SERVER_PORT");
    
    if (port == null) {
      port = System.getProperty("ECHO_SERVER_PORT", "8080");
    }
    
    return Integer.parseInt(port);
  }
}