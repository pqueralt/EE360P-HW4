//prq63 cc47696

import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

  private static InventoryOperations inventory;
  private static ArrayList<String> otherServers; //IPaddress:Port
  private static String thisServer;

  public synchronized static ArrayList<String> getOtherServers() {
    return otherServers;
  }

  public synchronized static String getThisServer(){
    return thisServer;
  }

  public synchronized static void removeServer(int i){
    otherServers.remove(i);
  }

  public synchronized static InventoryOperations getInventory() {
    return inventory;
  }

  public static void main (String[] args) {

    Scanner sc = new Scanner(System.in);
    int myID = sc.nextInt();
    int numServer = sc.nextInt();
    String inventoryPath = sc.next();

    inventory = new InventoryOperations(inventoryPath);
    thisServer = ""; // IP address and port of this server
    otherServers = new ArrayList<String>(); //IP address and ports of other servers

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

    Lamport lamport = new Lamport(thisServer, otherServers);

    while (true) {

      // TODO: start server socket to communicate with clients and other servers
      try{

        ServerSocket listener = new ServerSocket(Integer.valueOf(thisServer.split(":")[1]));
        Socket s;

        while( (s = listener.accept()) != null){

          Thread serverSender = new ServerSender(s, otherServers, thisServer);
          serverSender.start();

          /*Thread serverListener = new ServerListener(s, otherServers, thisServer);
          serverListener.start();*/
          Scanner input = new Scanner(s.getInputStream());
          String cmd = input.nextLine();
          String[] receivedCmd = cmd.split("-");
          if (receivedCmd[0].equals("CLIENT")){
            System.out.println("Server receives from client: " + cmd);
            System.out.println("Sending client request to lamport");
            lamport.request(cmd);

          } else if (receivedCmd[0].equals("REQUEST")){

            System.out.println("Receives request");
            lamport.receiveRequest(cmd);

          } else if (receivedCmd[0].equals("ACK")){

            String result = lamport.receiveAck(cmd);
            if (!result.equals("")){
              System.out.println(result); 
            }

          } else if (receivedCmd[0].equals("RELEASE")) {

            lamport.receiveRelease(cmd);

          }

        }

      } catch (Exception e){
        System.err.println(e);
      }

    }

  }

}
