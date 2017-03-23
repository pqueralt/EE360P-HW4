import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public class ServerListener extends Thread{

  ArrayList<String> servers;
  String thisServer;

  public ServerListener(ArrayList<String> servers, String thisServer){
    this.servers = servers;
    this.thisServer = thisServer;
  }

  public void run(){

    //set up listener sockets for all other servers
    //wait for pings every 100ms from each socket
    try{

      System.out.println("Setting up socket...");

      String[] ipport = thisServer.split(":");
      String ip = ipport[0];
      int port = Integer.valueOf(ipport[1]);

      InetAddress ia = InetAddress.getByName(ip);
      Socket socket = new Socket(ia, port);
      socket.setSoTimeout(100);

      Scanner din = new Scanner(socket.getInputStream());

      System.out.println("listening...");
      while(true){

          Thread.sleep(100);
          if(!din.hasNext()){ //server is no longer sending ping
            System.out.println("crash");
            break;
          } else {
            ArrayList<String> results = new ArrayList<String>();
            while(din.hasNext()){
              String returnedVal = din.next();
              results.add(returnedVal);
            }
            if (results.size() != servers.size()){
              System.out.println("at least one server not responding...");
              for (int i = 0; i < servers.size(); i++){
                if (!results.contains(servers.get(i))){
                  System.out.println(servers.get(i) + " has failed");
                  servers.remove(i);
                  i--;
                }
              }
            }

          }

      }

    } catch (Exception e){
      System.err.println(e);
    }



  }

}
