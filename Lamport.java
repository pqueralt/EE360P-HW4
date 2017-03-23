import java.util.*;
import java.util.Collections;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public class Lamport {

  private ArrayList<Request> queue; // Keep "queue" as ArrayList so we can sort it according to timestamp
  private int numAcks;
  private
  private boolean released;
  private RequestComparator reqComp;
  private int serverID;

  public Lamport(int myID) {
    serverID = myID;
    reqComp = new RequestComparator();
    released = true;
  }

  public void addRequest(Request req) {
    queue.add(req);
    Collections.sort(queue, reqComp); // Re-sort every time a new request is added
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

    //receiving "REQUEST"-UNIQUEID-TIMESTAMP-CMD
    //sending "ACK"-UNIQUEID-TIMESTAMP ?

  }

  //TODO: RECEIVE ACKNOWLEDGEMENT
  //numAcks := numAcks + 1;
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  public void receiveAck(String[] cmd){



  }

  //TODO: RECEIVE RELEASE
  //delete the request by Pj from q
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  public void receiveRelease(String[] cmd){
    released = false;
  }

  //TODO: RELEASE
  //send release to all processes;
  public void release() throws Exception {
    ArrayList<String> servers = Server.getOtherServers();

    for(String s : servers) {
      String[] info = s.split(":");
      InetAddress ia = InetAddress.getByName(info[0]);
      Socket socket = new Socket(ia, Integer.parseInt(info[1]));
      Scanner din = new Scanner(socket.getInputStream());
      PrintWriter pout = new PrintWriter(socket.getOutputStream());
      pout.println("RELEASE");
      pout.flush();
      socket.close();
    }
  }

}


/*
  System.getCurrentTimeMillis(); -- to get timestamp when wanting to send a request
*/
