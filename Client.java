//prq63 cc47696

import java.util.Scanner;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Client {
  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);
    int numServer = Integer.valueOf(sc.nextLine());

    ArrayList<String> servers = new ArrayList<>();

    for (int i = 0; i < numServer; i++) {
      // TODO: parse inputs to get the ips and ports of servers
      String str = sc.nextLine();
      servers.add(str);
    }



    while(sc.hasNextLine()) {
      String cmd = sc.nextLine();
      System.out.println("You just entered command..."+ cmd);

      for (int i = 0; i < servers.size(); i++){
        System.out.println("Connecting to... " + servers.get(i));
        ClientThread clientThread = new ClientThread(servers.get(i), cmd);
        clientThread.start();
        System.out.println("Waiting to receive result from server");
        try{
          clientThread.join();
        } catch (InterruptedException e){};

        String result = clientThread.getResult();

        if (result.equals("crash")){ //server fail
          System.out.println("SERVER CRASHED");

          servers.remove(i); //server removed from viable list
          i--;

        } else { //server success
          System.out.print(result);
          break;
        }

      }

    }
  }
}
