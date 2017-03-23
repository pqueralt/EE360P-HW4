//prq63 cc47696

import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

  private static InventoryOperations inventory;
  private static ArrayList<String> otherServers; //IPaddress:Port
  private static String thisServer;

  public Server(String inventoryPath){
    inventory = new InventoryOperations(inventoryPath);
    thisServer = ""; // IP address and port of this server
    otherServers = new ArrayList<String>(); //IP address and ports of other servers
  }

  public static ArrayList<String> getOtherServers() {
    return otherServers;
  }

  public static void main (String[] args) {

    Scanner sc = new Scanner(System.in);
    int myID = sc.nextInt();
    int numServer = sc.nextInt();
    String inventoryPath = sc.next();

    Server server = new Server(inventoryPath);
    Lamport lamport = new Lamport(myID);

    System.out.println("[DEBUG] my id: " + myID);
    System.out.println("[DEBUG] numServer: " + numServer);
    System.out.println("[DEBUG] inventory path: " + inventoryPath);

    for (int i = 0; i < numServer; i++) {
      // parse inputs to get the ips and ports of servers
      String str = sc.next();
      System.out.println("address for server " + i + ": " + str);

      if (i == myID - 1){
          thisServer = str;
      } else {
          otherServers.add(str);
      }

    }

    while (true) {

      // TODO: start server socket to communicate with clients and other servers
      try{

        ServerSocket listener = new ServerSocket(Integer.valueOf(thisServer.split(":")[1]));
        Socket s;

        while( (s = listener.accept()) != null){

          Thread serverSender = new ServerSender(s);
          serverSender.start();

          Thread serverListener = new ServerListener(otherServers, thisServer);
          serverListener.start();

          Scanner input = new Scanner(s.getInputStream());
          String cmd = input.nextLine();

          String[] receivedCmd = cmd.split("-");
          if (receivedCmd[0].equals("CLIENT")){
            System.out.println("Server receives from client: " + receivedCmd[1]);

            lamport.request(receivedCmd);

          } else if (receivedCmd[0].equals("REQUEST")){

            lamport.receiveRequest(receivedCmd);

          } else if (receivedCmd[0].equals("ACK")){

            lamport.receiveAck(receivedCmd);

          } else if (receivedCmd[0].equals("RELEASE")) {

            lamport.receiveRelease(receivedCmd);

          }

        }

      } catch (Exception e){
        System.err.println(e);
      }

    }

  }

}
