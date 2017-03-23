import java.util.*;
import java.util.Collections;

public class Lamport {

  ArrayList<Request> queue; // Keep "queue" as ArrayList so we can sort it according to timestamp
  int numAcks;

  boolean released;
  RequestComparator reqComp;
  Server server; // The server that this Lamport object belongs to
  int serverID;

  public Lamport(Server s, int myID) {
    server = s;
    serverID = myID;
    reqComp = new RequestComparator();
  }

  public void addRequest(Request req) {
    queue.add(req);
    Collections.sort(queue, reqComp); // Re-sort every time a new request is added
  }

  //ADDED IN*********************************************************

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


    //if entering CS...

  }

  //TODO: RECEIVE RELEASE
  //delete the request by Pj from q
  //if (numAcks = N − 1) and Pi’s request smallest in q then enter critical section;
  public void receiveRelease(String[] cmd){

  }

  //TODO: RELEASE
  //send release to all processes;
  public void release(){

  }

}


/*
  System.getCurrentTimeMillis(); -- to get timestamp when wanting to send a request
*/
