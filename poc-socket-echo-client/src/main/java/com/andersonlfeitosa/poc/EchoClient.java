package com.andersonlfeitosa.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;

public class EchoClient {
  
  private static final String template = "message %s!";
  
  private static final AtomicLong counter = new AtomicLong();

  private static final long SLEEP_TIME = 1000;
  
  public static void main(String[] args) throws IOException, InterruptedException {

    String hostName = getEchoServerHostname();
    int portNumber = getEchoServerPort();
    
    System.out.printf("ECHO CLIENT PARAMS:\nECHO_SERVER_HOSTNAME=%1$s\nECHO_SERVER_PORT=%2$s\n", hostName, portNumber);
    
    try (Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))) {
      String message = null;
      while (true) {
        message = String.format(template, counter.incrementAndGet());
        System.out.println("client => send: " + message);
        out.println(message);
        System.out.println("client => echo: " + in.readLine());
        Thread.sleep(SLEEP_TIME);
      }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);
    }
  }
  
  private static String getEchoServerHostname() {
    String hostname = System.getenv("ECHO_SERVER_HOSTNAME");
    
    if (hostname == null) {
      hostname = System.getProperty("ECHO_SERVER_HOSTNAME", "localhost");
    }
    
    return hostname;
  }

  private static int getEchoServerPort() {
    String port = System.getenv("ECHO_SERVER_PORT");
    
    if (port == null) {
      port = System.getProperty("ECHO_SERVER_PORT", "8080");
    }
    
    return Integer.parseInt(port);
  }
  
}