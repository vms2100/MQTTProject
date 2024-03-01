//Necessary Imports
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher extends Thread {
    // Get the necessary Global Variables from the Main class
    static String URL = Main.PublisherURL; // IP of the broker to connect to
    static String User = Main.PublisherUser; // User for connecting to the broker
    static String Password = Main.PublisherPassword; // Password for connecting to the broker

    // Global variables only
    static MqttClient Publisher; // Client of type "Publisher"
    static boolean isConnected = false; // Boolean to control the connection status
    static MqttConnectOptions options = new MqttConnectOptions(); // Connection options with the broker

    // Create the MQTT client
    public void Start() {
        try {
            Publisher = new MqttClient("tcp://"+URL, "SEGAL"); // Create the MQTT client "SEGAL"
            options.setUserName(User); // Set the username for connecting to the broker
            options.setPassword(Password.toCharArray()); // Set the password for connecting to the broker
            connect(); // Function to connect the client to the broker
        } catch (MqttException e) {
        	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
        }
    }

    public void connectionLost(Throwable throwable) {
        isConnected = false;
        while (!isConnected) {
            try {
            		Publisher.reconnect();;
                	if (Publisher.isConnected()) {
                		isConnected = true;
                	}
            } catch (MqttException e) {
            	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
            }
        }
    }
    
    // Function to connect the client to the broker
    // Attempts to connect to the broker until a successful connection is established
    public static void connect() {
        while (!isConnected) { // Loop until connected
            try {
                Publisher.connect(options);; // Attempt to connect the client
                if (Publisher.isConnected()) { // Check if connected
                    isConnected = true; // Set the boolean to true to exit the loop
                }
            } catch (MqttException e) {
            	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
            }
        }
    }

    // Function to send MQTT message
    // Receives the message from DataProcessing Class and sends it via MQTT
    public void sendData(String topic, String dataToSend) {
        MqttMessage mqttData = new MqttMessage(dataToSend.getBytes()); // Convert the message from String to bytes to send via MQTT
        mqttData.setQos(2); // Set the QoS to 2 for the nature of the project and the best QoS level
        try {
            Publisher.publish(topic, mqttData); // Publish the message to the topic
        } catch (MqttException e) {
        	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
        }
    }
}
