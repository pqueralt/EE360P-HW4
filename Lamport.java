import java.util.*;
import java.util.Collections;

public class Lamport {

  ArrayList<Request> requests; // Keep "queue" as ArrayList so we can sort it according to timestamp
  boolean released;
  RequestComparator reqComp;
  Server server; // The server that this Lamport object belongs to

  public void Lamport(Server s) {
    server = s;
    reqComp = new RequestComparator();
  }

  public void addRequest(Request req) {
    requests.add(req);
    Collections.sort(requests, reqComp); // Re-sort every time a new request is added
  }

}


/*
  System.getCurrentTimeMillis(); -- to get timestamp when wanting to send a request
*/
