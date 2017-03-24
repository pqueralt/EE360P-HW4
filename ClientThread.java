//prq63 cc47696

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

class ClientThread extends Thread{

  String ip;
  int port;
  String cmd;
  String result = "";

  public ClientThread(String server, String cmd){
    this.ip = server.split(":")[0];
    this.port = Integer.valueOf(server.split(":")[1]);
    this.cmd = cmd;
  }

  public String getResult(){
    return result;
  }

  public void run(){

    try{

      InetAddress ia = InetAddress.getByName(ip);
      Socket socket = new Socket(ia, port);
      socket.setSoTimeout(100);

      Scanner din = new Scanner(socket.getInputStream());
      PrintWriter pout = new PrintWriter(socket.getOutputStream());
      pout.println("CLIENT-"+cmd); //append client code
      pout.flush();

      while(true){
          Thread.sleep(100);
          if(!din.hasNextLine()){ //server is no longer sending ping
            result = "crash";
            break;
          } else {
            String returnedVal = din.nextLine(); //check if ping or result
            if (!returnedVal.contains("127.0.0.1")){
              while (!returnedVal.contains("127.0.0.1")){
                //System.out.println("client receives..." + returnedVal);
                result += returnedVal;
                returnedVal = din.nextLine();
              }
              break;
            }
          }
      }

    } catch (Exception e){
      System.err.println(e);
    }

  }

}
