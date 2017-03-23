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

        while( (s = listener.accept()) != null){

          Thread serverSender = new ServerSender(s);
          serverSender.start();

          Scanner input = new Scanner(s.getInputStream());
          String cmd = input.nextLine();

          String[] receivedCmd = cmd.split("-");
          if (receivedCmd[0].equals("CLIENT")){
            System.out.println("Server receives from client: " + receivedCmd[1]);
            //attempt to enter CS - add request to Q, send request to other servers
          } else if (receivedCmd[0].equals("SERVER")){
            //send acknowledgement back to server & add command to queue
          } else if (receivedCmd[0].equals("ACK")){
            //+1 acknowledgement
          } else if (receivedCmd[0].equals("RELEASE")) {
            //check if can enter CS next
          }

        }

      } catch (Exception e){
        System.err.println(e);
      }

      // TODO: parse the inventory file

      // TODO: handle request from client






    }


  }
}
