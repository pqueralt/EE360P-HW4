//prq63 cc47696

import java.io.*;
import java.util.*;
import java.net.*;

public class ServerSender extends Thread{

  Socket socket;
  ArrayList<String> servers;
  String thisServer;
  ArrayList<PrintWriter> outputs;

  public ServerSender(Socket s, ArrayList<String> servers, String thisServer){
    socket = s;
    servers = servers;
    thisServer = thisServer;
    outputs = new ArrayList<PrintWriter>();
  }

  public void run(){

    try{

      outputs.add(new PrintWriter(socket.getOutputStream()));

      /*for (int i = 0; i < servers.size(); i++){

        System.out.println("size of list of server is..."+servers.size());
        String[] ipport = servers.get(i).split(":");
        String ip = ipport[0];
        int port = Integer.valueOf(ipport[1]);

        InetAddress ia = InetAddress.getByName(ip);
        Socket socket = new Socket(ia, port);
        socket.setSoTimeout(100);

        PrintWriter pout = new PrintWriter(socket.getOutputStream());

        outputs.add(pout);

      }*/

      while(true){

        for (int i = 0; i < outputs.size(); i++){
          PrintWriter pout = outputs.get(i);
          pout.println("ping");
          pout.flush();
        }

        Thread.sleep(100);
      }
    } catch (Exception e){
      System.err.println(e);
    }

  }

}
