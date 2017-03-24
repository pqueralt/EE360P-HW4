//prq63 cc47696

import java.util.*;
import java.util.Collections;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public class Lamport {

  private ArrayList<Request> queue; // Keep "queue" as ArrayList so we can sort it according to timestamp
  private RequestComparator reqComp;
  private String serverInfo; // IPaddress:port\
  private ArrayList<String> otherServers;

  public Lamport(String inf, ArrayList<String> servers) {
    serverInfo = inf;
    reqComp = new RequestComparator();
    queue = new ArrayList<Request>();
    this.otherServers = servers;
  }

  //"CLIENT-<CMD>"
  public synchronized void request(String req){
    System.out.println("Lamport request was passed: \"" + req + "\"");
    //Pattern for request message:
    String[] s = req.split("-");
    Request r = new Request(System.currentTimeMillis(), s[1], serverInfo);
    queue.add(r);
    Collections.sort(queue, reqComp); // Re-sort every time a new request is added


    for (int i = 0; i < otherServers.size(); i++){
      String message = "REQUEST-" + String.valueOf(r.getRequestId()) +
        "-" + serverInfo +
        "-" + String.valueOf(r.getTimestamp()) +
        "-" + r.getCommand();

      sendMessage(otherServers.get(i), message);
    }
  }

  // "REQUEST-<REQUESTID>-<IPADDRESS:PORT>-<TIMESTAMP>-<CMD>"
  public synchronized void receiveRequest(String serverReq){

    System.out.println("Lamport receiveRequest was passed: \"" + serverReq + "\"");
    String[] s = serverReq.split("-");
    Request req = new Request(Long.parseLong(s[3]), s[4], serverInfo);
    queue.add(req);
    Collections.sort(queue, reqComp); // Re-sort every time a new request is added

    //sending "ACK-<REQUESTID>"
    sendMessage(s[2], "ACK-" + s[1]);

  }

  // "ACK-<REQUESTID>"
  public synchronized String receiveAck(String ack){
    System.out.println("Lamport receiveAck was passed: \"" + ack + "\"");

    String result = "";

    String[] s = ack.split("-");
    int rId = Integer.parseInt(s[1]);
    for(Request r : queue) {
      if(r.getRequestId() == rId) {
        r.acknowledge();

        if(queue.indexOf(r) == 0 && r.getNumAcks() >= Server.getOtherServers().size()) {
          // Enter CS? call function to execute CS logic here
          System.out.println("free to enter CS");
          result = executeCommand(r.getCommand());
          System.out.println(result);
          release();
        }
        break;
      }
    }

    return result;

  }

  // "RELEASE" - Once a request is released, execute its command
  public synchronized String receiveRelease(String cmd) {
    System.out.println("Lamport receiveRelease was passed: \"" + cmd + "\"");

    String result = "";

    Request r = queue.remove(0);
    executeCommand(r.getCommand());

    if (queue.size() > 0){
      Request headOfQ = queue.get(0);
      if (headOfQ.getServerInfo().equals(serverInfo) && headOfQ.getNumAcks() >= Server.getOtherServers().size()){
        System.out.println("free to enter CS");
        result = executeCommand(headOfQ.getCommand());
        System.out.println(result);
        release();
      }
    }

    return result;

  }

  public synchronized String executeCommand(String cmd) {
    String[] tokens = cmd.split(" ");
    InventoryOperations inventory = Server.getInventory();
    if (tokens[0].equals("purchase")) {
      return inventory.purchase(tokens[1], tokens[2], Integer.valueOf(tokens[3]));

    } else if (tokens[0].equals("cancel")) {
      return inventory.cancel(Integer.valueOf(tokens[1]));

    } else if (tokens[0].equals("search")) {
      return inventory.search(tokens[1]);

    } else if (tokens[0].equals("list")) {
      return inventory.list();

    } else {
      return("ERROR: No such command");
    }
  }

  public void release() {
    queue.remove(0);
    for(String s : otherServers) {
      sendMessage(s, "RELEASE");
    }

  }

  private synchronized void sendMessage(String serverInfo, String message) {
    try{
      String[] info = serverInfo.split(":");
      InetAddress ia = InetAddress.getByName(info[0]);
      Socket socket = new Socket(ia, Integer.parseInt(info[1]));
      Scanner din = new Scanner(socket.getInputStream());
      PrintWriter pout = new PrintWriter(socket.getOutputStream());
      pout.println(message);
      pout.flush();
      socket.close();
    } catch(Exception e) {
      // do nothing
    }
  }


}


/*
  System.currentTimeMillis(); -- to get timestamp when wanting to send a request
*/
