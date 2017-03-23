import java.io.*;
import java.util.*;
import java.net.*;

public class ServerSender extends Thread{

  Socket socket;

  public ServerSender(Socket s){
    socket = s;
  }

  public void run(){

    try{
      PrintWriter pout = new PrintWriter(socket.getOutputStream());

      while(true){
        pout.println("ping");
        pout.flush();
        Thread.sleep(100);
      }
    } catch (Exception e){
      System.err.println(e);
    }

  }

}
