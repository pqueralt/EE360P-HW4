//prq63 cc47696

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public class ServerListener extends Thread{

  Socket s;
  ArrayList<String> servers;
  String thisServer;

  public ServerListener(Socket s,ArrayList<String> servers, String thisServer){
    this.s = s;
    this.servers = servers;
    this.thisServer = thisServer;
  }

  public void run(){

    //set up listener sockets for all other servers
    //wait for pings every 100ms from each socket
    try{

      Scanner din = new Scanner(s.getInputStream());

      while(true){

          Thread.sleep(100);
          if(!din.hasNext()){ //server is no longer sending ping
            break;
          } else {
            ArrayList<String> results = new ArrayList<String>();
            while(din.hasNextLine()){
              String returnedVal = din.nextLine();
              results.add(returnedVal);
            }
            if (results.size() != servers.size()){
              for (int i = 0; i < servers.size(); i++){
                if (!results.contains(servers.get(i))){
                  System.out.println(servers.get(i) + " has failed");
                  Server.removeServer(i);
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
