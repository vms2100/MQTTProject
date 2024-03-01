// Necessary Imports
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    // Modifiable Variables
	static int ID_Institution=1;	//ID From Institution "SEGAL" in database
	static String DBLocation="jdbc:sqlite:F:/BDFinal/BaseDados.db";	//Path to database
    static Path path = Paths.get("F:/Maregrafos/LogsSegal"); // Directory for Log Location
    static String PublisherURL="10.0.7.45:1833";
    static String PublisherUser="";
    static String PublisherPassword="";

    // Global Variables (Do Not Modify)
    static ArrayList<MQTTClient> List = new ArrayList<>(); // List of Clients
    static ArrayList<MQTTClient> TempList = new ArrayList<>(); // Temporary List of Clients
    static ArrayList<Thread> ThreadList = new ArrayList<>(); // List of Threads with Active Clients
    static ArrayList<Thread> TempThreadList = new ArrayList<>(); // List of Threads with All Clients
    static Publisher publisher = new Publisher(); //Create the Publisher

    // Main Function
    // In this function, the "SEGAL" Publisher is started
    // It checks if the "Logs" directories exist
    // It retrieves from the database which Maregraph "SEGAL" controls
    // It compares which clients should be connected and which should not
    public static void main(String[] args) {
        publisher.Start(); // Start the Publisher
        while (true) {
            checkDirectories(); // Check Directories
            ConnectionDB.GetMaregraphData(); // Connect to the Database
            if (!AuxFunc.equals(List, TempList)) { // Compare the Final and Temporary Client Lists
                List.clear();	//Clear the Final List of Clients
                List.addAll(TempList);	//Copy all Clients from Temporary List to the Final
                runClients(); // Call the Function to Create and Connect the Clients
            }
            AuxFunc.Delay(60); // Repeat all these steps every 60 seconds
        }
    }

    // Function to check the Directories
    // If the directory specified in the variables does not exist, it creates the directory
    public static void checkDirectories() {
        if (!Files.exists(path)) { // If the path does not exist
            File directory = new File(path.toString()); // Create the directory path
            directory.mkdirs(); // Create the Directory
        }
    }

    // Function to initialize the Clients and add them to the Client List
    // Clears the Temporary Client List
    // Compares with the Final Client List obtained from the Database
    // If there are missing clients, it creates and starts them
    // If there are extra clients, it disconnects them
    public static void runClients() {
        TempThreadList.clear(); // Clear the Temporary Thread List of Clients
        for (int i = 0; i < List.size(); i++) {
            MQTTClient mqtt = List.get(i); // Create the Client
            Thread thread = new Thread(mqtt); // Allocate the Thread for the Client
            if (mqtt.client == null) { // If the client has not been initialized yet
                ThreadList.add(thread); // Add the new client to the List of Client Threads
                TempThreadList.add(thread); // Also add it to the temporary list
                thread.start(); // Start the Thread with the client
            } else {
                TempThreadList.add(thread); // If the client has already been initialized, simply add it to the Temporary List
            }
        }

        if (!AuxFunc.equals(ThreadList, TempThreadList)) { // Function to compare the Final and Temporary Thread Lists
            for (int i = 0; i < ThreadList.size(); i++) {
                if (!TempThreadList.contains(ThreadList.get(i))) { // If any client is not in the Temporary Thread List, remove it from the Final Thread List
                    ThreadList.get(i).interrupt(); // Stop the Client's Thread
                    ThreadList.remove(i); // Remove from the Final List
                }
            }
        }
    }
}
