//prq63 cc47696

import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
  public static void main (String[] args) {

    Scanner sc = new Scanner(System.in);
    int myID = sc.nextInt();
    int numServer = sc.nextInt();
    String inventoryPath = sc.next();

    String thisServer = ""; // IP address and port of this server
    ArrayList<String> otherServers = new ArrayList<String>(); //IP address and ports of other servers

    /*
    System.out.println("[DEBUG] my id: " + myID);
    System.out.println("[DEBUG] numServer: " + numServer);
    System.out.println("[DEBUG] inventory path: " + inventoryPath);
    */

    for (int i = 0; i < numServer; i++) {
      // parse inputs to get the ips and ports of servers
      String str = sc.next();

      if (i == myID - 1){
          thisServer = str;
      } else {
          otherServers.add(str);
      }

    }

    /*
    System.out.println("THIS SERVER " + thisServer);
    for (int i = 0; i < otherServers.size(); i++){
      System.out.println("OTHER SERVER " + otherServers.get(i));
    }
    */


    while (true) {

      // TODO: start server socket to communicate with clients and other servers
      try{
        ServerSocket listener = new ServerSocket(Integer.valueOf(thisServer.split(":")[1]));

        Socket s;
        s = listener.accept();
          Scanner input = new Scanner(s.getInputStream());

          while(!input.hasNextLine());
          System.out.println("aquiring command");
          String cmd = input.nextLine();
          System.out.println(cmd);

          //TODO
          //check command - if from other server, acknowledge and add to quue
          //if from client - send to other servers
          //if commnand is release statement, check if can enter CS next

          Thread serverSender = new ServerSender(s);
          serverSender.start();



      } catch (Exception e){
        System.err.println(e);
      }

      while(true);
      // TODO: parse the inventory file

      // TODO: handle request from client






    }


  }
}
