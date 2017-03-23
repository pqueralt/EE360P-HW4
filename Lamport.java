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
  private int serverID;

  public Lamport(int myID) {
    serverID = myID;
    reqComp = new RequestComparator();
  }

  //TODO: REQUEST
  //send request with (logicalClock,i) to all other processes;
  //numAcks := 0;
  public void request(String[] cmd){

    //Pattern for request message: "REQUEST"-UNIQUEID-TIMESTAMP-CMD?

  }

  //TODO: RECEIVE REQUEST
  //insert (ts, j) in q;
  //send(ack, logicalClock) to Pj
  public void receiveRequest(String[] cmd){

    Request req = new Request(Long.parseLong(cmd[2]), cmd[3]);
    queue.add(req);
    Collections.sort(queue, reqComp); // Re-sort every time a new request is added
    //receiving "REQUEST"-UNIQUEID-TIMESTAMP-CMD
    //sending "ACK"-UNIQUEID-TIMESTAMP ?

  }

  //TODO: RECEIVE ACKNOWLEDGEMENT
  //numAcks := numAcks + 1;
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  public void receiveAck(String[] cmd){
    int rId = Integer.parseInt(cmd[1]);
    for(Request r : queue) {
      if(r.getRequestId() == rId) {
        r.acknowledge();
        if(queue.indexOf(r) == 0 && r.getNumAcks() >= Server.getOtherServers().size()) {
          // Enter CS? call function to execute CS logic here
        }
        break;
      }
    }
  }

  //TODO: RECEIVE RELEASE
  //delete the request by Pj from q
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  public void receiveRelease(String[] cmd){

  }

  //TODO: RELEASE
  //send release to all processes;
  public void release() throws Exception {
    ArrayList<String> servers = Server.getOtherServers();

    for(String s : servers) {
      sendMessage(s, "RELEASE");
    }
  }

  private void sendMessage(String serverInfo, String message) throws Exception {
      String[] info = serverInfo.split(":");
      InetAddress ia = InetAddress.getByName(info[0]);
      Socket socket = new Socket(ia, Integer.parseInt(info[1]));
      Scanner din = new Scanner(socket.getInputStream());
      PrintWriter pout = new PrintWriter(socket.getOutputStream());
      pout.println(message);
      pout.flush();
      socket.close();
  }
}


/*
  System.getCurrentTimeMillis(); -- to get timestamp when wanting to send a request
*/
