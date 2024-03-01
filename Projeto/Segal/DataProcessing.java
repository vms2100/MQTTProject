//Necessary Imports
import java.util.ArrayList;

public class DataProcessing {
    // Global Variables
    public static ArrayList<String> list = new ArrayList<>(); // List with the topic and name of the maregraph to which the publisher needs to send the message
    public static ArrayList<String> TempList = new ArrayList<>(); // Same but to be pasted
    // Function to process the message and send it to the Publisher for publishing
    public static void Send(String message, String Name_Maregraph) {
        getDBInfo(Name_Maregraph,message); // Retrieve information from the database
        if(list.isEmpty()){	// If all message have already been sent
        	synchronized (TempList) { // To lock the TempList
        		list.addAll(TempList); // Past all other messages to the list
            	TempList.clear(); // Clear the TempList 
        	}
        }
        for (String Data : list) {
            String[] Parts = Data.split("\\|"); // Split at the "|" character
            String Topic = Parts[0]; // Topic comes before "|"
            String Name = Parts[1];// Maregraph Name comes after "|"
            String Message = Parts[2]; // Message comes after "|"
            String newMessage = Name + "|" + Message ; // Create a new message with the Maregraph Name and the message
            Main.publisher.sendData(Topic, newMessage); // The publisher publishes the new message to the topic
        }
        list.clear(); // Clear the list
    }

    // Function to retrieve information from the database
    public static void getDBInfo(String Name_Maregraph,String message) {
        ConnectionDB.getPublisher(Name_Maregraph,message); // Function that retrieves who to send the messages to from the database
    }
}
