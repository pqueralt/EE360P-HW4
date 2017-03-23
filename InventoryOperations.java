//prq63 cc47696

import java.net.*;
import java.io.*;
import java.util.*;

public class InventoryOperations {

  private ArrayList<ArrayList<Object>> orders; // ArrayList of shape [orderid, username, productname, quantity]
  private HashMap<String, Integer> inventory;
  private int nextOrderID;

  public InventoryOperations(String inventoryFile){
    orders = new ArrayList();
    inventory = new HashMap();
    this.setupInventory(inventoryFile);
    nextOrderID = 1;
  }

  private void setupInventory(String inventoryFile) { // Sets up inventory
    try{
      Scanner in = new Scanner(new File(inventoryFile));
      while(in.hasNextLine()) {
        String[] item = in.nextLine().split(" ");
        inventory.put(item[0], Integer.decode(item[1]));
      }
    } catch (FileNotFoundException e){
      System.err.println(e);
    }
  }

  public String purchase(String username, String productName, int quantity) {
    if (!inventory.containsKey(productName)) {
      return "Not Available - We do not sell this product";
    }

    int amountAvailable = inventory.get(productName);
    if (amountAvailable < quantity) {
      return "Not Available - Not enough items";
    } else {
      inventory.put(productName, amountAvailable - quantity);
      ArrayList o = new ArrayList();
      o.add(nextOrderID);
      o.add(username);
      o.add(productName);
      o.add(quantity);
      orders.add(o);
      String result = "Your order has been placed, " + nextOrderID + " " + username + " " + productName + " " + quantity;
      nextOrderID++;
      return result;
    }
  }

  public synchronized String cancel(int orderID) {
    for (int i = 0; i < orders.size(); i++) {
      ArrayList<Object> o = orders.get(i);
      if (((Integer)o.get(0)) == orderID) {
        String item = (String) o.get(2);
        int quantity = (Integer) o.get(3);
        inventory.put(item, quantity + inventory.get(item));
        orders.remove(i);
        return "Order " + String.valueOf(orderID) + " is canceled";
      }
    }
    return String.valueOf(orderID) + " not found, no such order";
  }

  public String search(String username) {
    String userOrders = "";
    for (ArrayList<Object> o : orders) {
      if (o.get(1).equals(username)) {
        if(userOrders.length() == 0) {
          userOrders = o.get(0) + ", " + o.get(2) + ", " + o.get(3);
        }
        else {
          userOrders = userOrders + "\n" + o.get(0) + ", " + o.get(2) + ", " + o.get(3);
        }
      }
    }
    if (userOrders.length() == 0) {
      return "No order found for " + username;
    } else {
      return userOrders;
    }
  }

  public String list() {
    String inventoryString = "";
    Set<String> keys = inventory.keySet();
    for (String k : keys) {
      inventoryString = inventoryString + "\n" + k + " " + inventory.get(k);
    }
    if(inventoryString.length() > 0) {
      inventoryString = inventoryString.substring(1, inventoryString.length());
    }
    return inventoryString;
  }

}
