//Necessary Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.paho.client.mqttv3.MqttException;

public class ConnectionDB {
    // Get the necessary Global Variables from the Main class
    static int ID_Institution = Main.ID_Institution;
    static String DBLocation = Main.DBLocation;

    // Function to retrieve the necessary Maregraph information for connecting to them
    public static void GetMaregraphData() {
        Main.TempList.clear(); // Clear the Client List in the Main class
        try {
            Connection conn = DriverManager.getConnection(DBLocation); // Connect to the Database
            Statement stmt = conn.createStatement(); // Prepare the SQL statement
            // This SQL statement check which Maregraphs "SEGAL" has access to
            ResultSet rs = stmt.executeQuery("SELECT M.*, A.Name FROM Analyze A, Maregraph M WHERE A.ID_Institution = " + ID_Institution + " AND M.ID_Maregraph = A.ID_Maregraph"); // Retrieve the information response from the SQL query
            if (!rs.isBeforeFirst()) { // If it is empty, do nothing
                rs.close(); // Close the result set
                stmt.close(); // Close the statement
                conn.close(); // Close the database connection
                return; // Exit the function
            }
            while (rs.next()) { // If it is not empty
                String URL = rs.getString("URL"); // URL receives the "URL" string from the database
                String User = rs.getString("User"); // User receives the "User" string from the database
                String Password = rs.getString("Password"); // Password receives the "Password" string from the database
                String MaregraphName = rs.getString("Name"); // MaregraphName receives the name of the maregraph assigned by "SEGAL"
                int ID_Maregraph = rs.getInt("ID_Maregraph"); // MaregraphID receives the Maregraph ID
                try {
                    MQTTClient client = new MQTTClient(URL, Integer.toString(ID_Institution), User, Password, ID_Maregraph, MaregraphName); // Create the Client
                    Main.TempList.add(client); // Add the Client to the Client List in the Main class
                } catch (MqttException e) {
                    System.out.println(e.getMessage()); // In case of error, display the error message
                }
            }
            rs.close(); // Close the result set
            stmt.close(); // Close the statement
            conn.close(); // Close the database connection
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // In case of error, display the error message
        }
    }

    // Function to retrieve information for the Publisher
    public static void getPublisher(String Name_Maregraph,String Message) {
        int ID_Maregraph = -1; // Initialize the ID_Maregraph variable to -1 (Impossible Case)
        try {
            Connection conn = DriverManager.getConnection(DBLocation); // Connect to the Database
            Statement stmt = conn.createStatement(); // Prepare the SQL statement
            // This SQL statement get the ID_Maregraph
            ResultSet rs = stmt.executeQuery("SELECT A.ID_Maregraph FROM Analyze A WHERE A.Name = '" + Name_Maregraph + "' AND A.ID_Institution = '1'"); // Obtain the ID_Maregraph
            if (rs.next()) { // If it is not empty
                ID_Maregraph = rs.getInt("ID_Maregraph"); // Get the ID_Maregraph
            }
            // This SQL statement check which Institutions analyze this Maregraph
            rs = stmt.executeQuery("SELECT I.Topic, A.Name FROM Analyze A, Institution I WHERE A.ID_Institution <> 1 AND A.ID_Maregraph = " + ID_Maregraph + " AND A.ID_Institution = I.ID_Institution");  // Retrieve the information response from the SQL query
            while (rs.next()) {
                String Topic = rs.getString("Topic"); // Obtain the Topic to which the Publisher should send the message
                String Name = rs.getString("Name"); // Obtain the name of the Maregraph
                String result = Topic + "|" + Name + "|" + Message; // Combine all this into a string
                DataProcessing.TempList.add(result); // Add this string to a list in DataProcessing Class
            }
            rs.close(); // Close the result set
            stmt.close(); // Close the statement
            conn.close(); // Close the database connection
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // In case of error, display the error message
        }
    }
}


