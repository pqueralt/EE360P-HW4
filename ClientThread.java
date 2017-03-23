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
          if(!din.hasNext()){ //server is no longer sending ping
            break;
          } else {
            String returnedVal = din.next(); //check if ping or result
          }
      }

    } catch (Exception e){
      System.err.println(e);
    }

  }

}
