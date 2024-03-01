//Necessary Imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class GetData {
    // Information required for the program, to be provided by the administrator
    static String URL = "tcp://10.0.7.45:1833"; // IP of the broker to connect to
    public static File file = new File("/home/x/Desktop/Projeto/Logs/Maregrafo/Send.txt"); // File to retrieve information from
    static String User = ""; // User for connecting to the broker, leave empty if not needed
    static String Password = ""; // Password for connecting to the broker, leave empty if not needed
    static double TimeToKeepInHours = 6; // Time to keep the messages in hours until connection is established with the router

    // Global Variables Only
    static MqttClient Publisher; // Client of type "Publisher"
    static boolean isConnected = false; // Boolean to control if it is connected
    static MqttConnectOptions options = new MqttConnectOptions(); // Connection options with the broker

    // Main Function
    // Create the MQTT client and call the function to read from the file
    public static void main(String[] args) {
        try {
            Publisher = new MqttClient(URL, "publisher"); // Create the MQTT client "Publisher"
            options.setUserName(User); // Set the user for the broker connection
            options.setPassword(Password.toCharArray()); // Set the password for the broker connection
            connect(); // Function to connect the client to the broker
        } catch (MqttException e) {
        	System.out.println(e.getMessage()); // In case of error, display the error message
        }
        readData(); // Function that reads the file
    }

    // Function to connect the client to the broker
    // Attempts to connect to the broker until a successful connection is established
    // Creates a thread to keep the file with real-time data for up to 6 hours until connected
    public static void connect() {
        KeepRealTimeData r = new KeepRealTimeData(); // Create a class to create a thread
        Thread KeepRealTime = new Thread(r); // Create the thread to control the file and keep only the last 6 hours of information
        KeepRealTime.start(); // Start the thread
        while (!isConnected) { // Loop until connected
            try {
                Publisher.connect(options); // Attempt to connect the client
                if (Publisher.isConnected()) { // Check if connected
                    KeepRealTime.interrupt(); // Turn off the thread to prevent information loss or a deadlock
                    isConnected = true; // Set the boolean to true to exit the loop
                }
            } catch (MqttException e) {
            	System.out.println(e.getMessage()); // In case of error, display the error message
            }
        }
    }

    // Function to read from the file
    // Reads the file line by line and sends each line to the function that sends the message
    public static void readData() {
        while (true) { // Infinite loop to check the file
            try {
                Scanner scanner = new Scanner(file); // Create a scanner to read the file
                if (file.length() > 0) { // Check if the file is not empty (overkill, but used to save resources)
                    while (scanner.hasNextLine()) { // Loop to read the entire file
                        if (!Publisher.isConnected()) { // Check if the client is connected
                            isConnected = false; // Set the boolean to false to indicate not connected
                            connect(); // Function to connect the client
                        } else {
                            sendData(scanner.nextLine()); // Function to send the message via MQTT
                            if (!scanner.hasNextLine()) { // Check if the end of the file is reached
                                FileWriter writer = new FileWriter(file, false); // Clear the contents of the file
                                writer.close(); // Close the writer as the file has been cleared
                            }
                        }
                    }
                }
                scanner.close(); // Close the scanner that reads the file
            } catch (IOException e) {
            	System.out.println(e.getMessage()); // In case of error, display the error message
            }
        }
    }

    // Function to send the MQTT message
    // Receives the message read by the readData function and sends it via MQTT
    public static void sendData(String dataToSend) {
        MqttMessage mqttData = new MqttMessage(dataToSend.getBytes()); // Convert the message from String to bytes to send via MQTT
        mqttData.setQos(2); // Set the QoS to 2, chosen based on the project's requirements and the optimal QoS
        try {
            Publisher.publish("Maregrafo", mqttData); // Send the message to the "Maregrafo" topic
        } catch (MqttException e) {
        	System.out.println(e.getMessage()); // In case of error, display the error message
        }
    }
}
