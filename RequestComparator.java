import java.util.*;

public class RequestComparator implements Comparator<Request> {
  public int compare(Request r1, Request r2) {
    return Long.compare(r1.getTimestamp(), r2.getTimestamp());
  }
}
