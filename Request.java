import java.util.Comparator;

public class Request {
  private long timestamp;
  private int serverId;
  
  public void Request(long ts, int sid) {
    timestamp = ts;
    serverId = sid;
  }

  public void Request(Long ts, String sid) {
      timestamp = ts;
      serverId = Integer.parseInt(sid);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getServerId() {
    return serverId;
  }

}
