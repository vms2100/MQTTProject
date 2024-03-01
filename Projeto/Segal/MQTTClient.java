//Necessary Imports
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTClient implements MqttCallback, Runnable {
    // Global Variables
    public MqttClient client;
    String Name_Maregraph, URL, ID_Client, User, Password;
    int ID_Maregraph;
    boolean isConnected = false;
    MqttConnectOptions options = new MqttConnectOptions();

    public MQTTClient(String URL, String ID_Client, String User, String Password, int ID_Maregraph, String Name_Maregraph) throws MqttException {
        this.ID_Maregraph = ID_Maregraph;
        this.URL = "tcp://" + URL;
        this.User = User;
        this.ID_Client = ID_Client;
        this.Password = Password;
        this.Name_Maregraph = Name_Maregraph;
    }

    // Function called when the client loses connection
    // In this function, the client enters a loop while trying to reconnect
    @Override
    public void connectionLost(Throwable throwable) {
        isConnected = false;
        while (!isConnected) {
            try {
            	client.reconnect();
                	if (client.isConnected()) {
                		isConnected = true;
                	}
            } catch (MqttException e) {
            	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
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
        options.setUserName(User); // Set the username
        options.setPassword(Password.toCharArray()); // Set the password
        options.setCleanSession(false); // Set whether the broker "forgets" the client
      
        try {
            client = new MqttClient(URL, ID_Client); // Create the client
        } catch (MqttException e) {
        	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
        }
        connect(); // Call the connect function to establish the client's connection to the broker
    }

    // Function that establishes the connection with the broker
    public void connect() {
        while (!isConnected) { // While it is not connected
            try {
                client.connect(options); // Client connects with the specified parameters
                if (client.isConnected()) { // If the client is connected
                    client.subscribe("Maregrafo", 2); // Subscribe to the "Maregrafo" topic
                    client.setCallback(this);
                    isConnected = true;
                }
            } catch (MqttException e) {
            	System.out.println("Error:"+e.getMessage()); // In case of error, display the error message
            }
        }
    }

    // Function that is called whenever the client receives a message
    // It checks if the topic is the one we want
    // Writes the message to the "SEGAL" log
    // Calls the DataProcessing function
    @Override
    public void messageArrived(String Topic, MqttMessage message) throws Exception {
        if (Topic.equals("Maregrafo")) {
            String data = new String(message.getPayload(), StandardCharsets.UTF_8);
            WriteLog.CreateLogFile(Main.path, Name_Maregraph, data);
            DataProcessing.Send(data, Name_Maregraph);
        }
    }

    // Function that checks if the client has been initialized
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        MQTTClient other = (MQTTClient) obj;
        return Objects.equals(ID_Client, other.ID_Client) && Objects.equals(ID_Maregraph, other.ID_Maregraph)
                && Objects.equals(Password, other.Password) && Objects.equals(URL, other.URL)
                && Objects.equals(User, other.User) && Objects.equals(Name_Maregraph, other.Name_Maregraph);
    }
}
