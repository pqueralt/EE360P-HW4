//prq63 cc47696

import java.util.Comparator;
import java.util.Random;
public class Request {
  private long timestamp;
  private int requestId;
  private int numAcks;
  private String command;

  public Request(long ts, String cmd) {
    timestamp = ts;
    command = cmd;
    Random r = new Random();
    requestId = r.nextInt();
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

  public int getServerId() {
    return 0;//serverId;
  }

  public int getRequestId() {
    return requestId;
  }

}
