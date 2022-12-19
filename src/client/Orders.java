package client;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.concurrent.ConcurrentHashMap;

public class Orders {
    public ConcurrentHashMap<Integer, String> startOrders = new ConcurrentHashMap<>();
    public ConcurrentHashMap<Integer, String> userOrders =new ConcurrentHashMap<>();


    public void setStartOrders(Document document) {
        NodeList nodeList = document.getElementsByTagName("orders").item(0).getChildNodes();
        int number = 1;
        for (int i = 0; i < nodeList.getLength(); i++) {
            String temp = nodeList.item(i).getNodeName();
            if (!temp.equals("#text")) {
                startOrders.put(number++, temp);
            }
        }
    }

    public void setUserOrders(Document document) {
        NodeList nodeList = document.getElementsByTagName("orders").item(0).getChildNodes();
        int number = 1;
        for (int i = 0; i < nodeList.getLength(); i++) {
            String temp = nodeList.item(i).getNodeName();
            if (!temp.equals("#text")) {
                userOrders.put(number++, temp);
            }
        }
    }

    public String getUserOrderByUserInput(String userInput) {
        try {
            int n = Integer.parseInt(userInput);
            if (userOrders.containsKey(n)) {
                return userOrders.get(n);
            }
            return "error";
        } catch (NumberFormatException e) {
            return "error";
        }
    }

    public String getStartOrderByUserInput(String userInput) {
        try {
            int n = Integer.parseInt(userInput);

            if (startOrders.containsKey(n)) {
                return startOrders.get(n);
            }
            return "error";

        } catch (NumberFormatException e) {
            return "error";
        }

    }

}
