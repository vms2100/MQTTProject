//Necessary Imports
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.paho.client.mqttv3.*;

public class Client {
    // Variables to be changed
    public static String BROKER = "10.0.7.45:1833"; // IP to "SEGAL" Broker
    public static int CLIENT_ID = 2;	// Client ID
    public static String Topic = "ipmatopics"; // Topic to subscribe
    static Path path = Paths.get("/Maregrafos/LogsIPMA"); // Path to log files

    // Main function
    // Here, the client is created and connected to the broker
    public static void main(String[] args) {
        checkDirectories();
        try {
            MQTTClient client = new MQTTClient(BROKER, CLIENT_ID); // Create the client
            Thread thread = new Thread(client); // Assign the client to a thread
            thread.start(); // Start the thread and the client
        } catch (MqttException e) {
            System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
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
}
