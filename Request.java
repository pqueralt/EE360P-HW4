//prq63 cc47696

import java.util.Comparator;
import java.util.Random;
import java.util.*;

public class Request {
  private long timestamp;
  private int requestId;
  private int numAcks;
  private String command;
  private String serverInfo;

  public Request(long ts, String cmd, String server) {
    timestamp = ts;
    command = cmd;
    Random r = new Random();
    requestId = r.nextInt();
    requestId = Math.abs(requestId);
    serverInfo = server; 
  }

  public String getServerInfo(){
    return serverInfo;
  }

  public String getCommand() {
    return command;
  }

  public void acknowledge() {
    numAcks++;
  }

  public int getNumAcks() {
    return numAcks;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getRequestId() {
    return requestId;
  }

}
