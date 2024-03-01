//Necessary Imports
import java.nio.charset.StandardCharsets;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTClient implements MqttCallback, Runnable {
    // Global variables
    public MqttClient ClientMqtt; // MQTT client
    String Topic = Client.Topic; // Retrieve the topic from the Client Class
    String URL; // URL to connect to broker
    int Clientid; // Client ID
    boolean isConnected = false;
    MqttConnectOptions options = new MqttConnectOptions(); // Connection parameters

    public MQTTClient(String URL, int clientId) throws MqttException {
        this.URL = "tcp://" + URL;
        this.Clientid = clientId;
    }

    // Function called when the client loses connection
    // In this function, the client enters a loop while trying to reconnect
    @Override
    public void connectionLost(Throwable throwable) {
        isConnected = false;
        while (!isConnected) {
            try {
                ClientMqtt.reconnect(); // Attempt to reconnect
                if (ClientMqtt.isConnected()) { // If connected
                    isConnected = true; // Update the variable to exit the loop
                }
            } catch (MqttException e) {
                System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
            }
        }
    }

    // Mandatory function without usefulness
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    // Function to provide the necessary attributes for the client to connect to the broker
    @Override
    public void run() {
        options.setCleanSession(false); // This function ensures that the broker remembers the client
        try {
            ClientMqtt = new MqttClient(URL, Integer.toString(Clientid)); // Create the MQTT client
        } catch (MqttException e) {
            System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
        }
        connect(); // Function to connect the client
    }

    // Function that establishes the connection with the broker
    public void connect() {
        while (!isConnected) {
            try {
                ClientMqtt.connect(options); // Connect to the broker with the specified parameters
                if (ClientMqtt.isConnected()) { // If the client is connected
                    ClientMqtt.subscribe(Topic, 2); // Subscribe to the topic with QoS 2
                    ClientMqtt.setCallback(this);
                    isConnected = true;
                }
            } catch (MqttException e) {
                System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
            }
        }
    }

    // Function that is called whenever the client receives a message
    // It checks if the topic is the one we want
    // Writes the message to the Client log
    @Override
    public void messageArrived(String TopicS, MqttMessage message) throws Exception {
        if (TopicS.equals(Topic)) { // Check if the topic is the intended one
            String data = new String(message.getPayload(), StandardCharsets.UTF_8); // Receive the message
            String[] Parts = data.split("\\|"); // Split the message
            String Name_Maregraph = Parts[0]; // Receive the name of the maregraph
            String Message = Parts[1]; // Receive the message itself
            WriteLog.CreateLogFile(Client.path, Name_Maregraph, Message); // Write the message to the log
        }
    }
}
