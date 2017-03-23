//prq63 cc47696

import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

  private static InventoryOperations inventory;
  int uniqueID;
  Queue<Integer> queue;
  int numAcks;
  //TODO: IMPLEMENT LOGICAL CLOCK

  public Server(String inventoryPath, int myID){
    inventory = new InventoryOperations(inventoryPath);
    queue = new LinkedList<Integer>();
    numAcks = 0;
    uniqueID = myID;
  }

  public static void main (String[] args) {

    Scanner sc = new Scanner(System.in);
    int myID = sc.nextInt();
    int numServer = sc.nextInt();
    String inventoryPath = sc.next();

    Server server = new Server(inventoryPath, myID);

    String thisServer = ""; // IP address and port of this server
    ArrayList<String> otherServers = new ArrayList<String>(); //IP address and ports of other servers

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

          Scanner input = new Scanner(s.getInputStream());
          String cmd = input.nextLine();

          String[] receivedCmd = cmd.split("-");
          if (receivedCmd[0].equals("CLIENT")){
            System.out.println("Server receives from client: " + receivedCmd[1]);

            server.request(receivedCmd);

          } else if (receivedCmd[0].equals("REQUEST")){

            server.receiveRequest(receivedCmd);

          } else if (receivedCmd[0].equals("ACK")){

            server.receiveAck(receivedCmd);

          } else if (receivedCmd[0].equals("RELEASE")) {

            server.receiveRelease(receivedCmd);

          }

        }

      } catch (Exception e){
        System.err.println(e);
      }

    }

  }

  //TODO: REQUEST
  //send request with (logicalClock,i) to all other processes;
  //numAcks := 0;
  private void request(String[] cmd){

    //Pattern for request message: "REQUEST"-UNIQUEID-TIMESTAMP-CMD?

  }

  //TODO: RECEIVE REQUEST
  //insert (ts, j) in q;
  //send(ack, logicalClock) to Pj
  private void receiveRequest(String[] cmd){

    //receiving "REQUEST"-UNIQUEID-TIMESTAMP-CMD
    //sending "ACK"-UNIQUEID-TIMESTAMP ?

  }

  //TODO: RECEIVE ACKNOWLEDGEMENT
  //numAcks := numAcks + 1;
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  private void receiveAck(String[] cmd){


    //if entering CS...

  }

  //TODO: RECEIVE RELEASE
  //delete the request by Pj from q
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  private void receiveRelease(String[] cmd){

  }

  //TODO: RELEASE
  //send release to all processes;
  private void release(){

  }

}
