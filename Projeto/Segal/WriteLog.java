//Necessary Imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class WriteLog {

    // Function to create the log file
    // Checks if the directory and file exist
    public static void CreateLogFile(Path path, String logName, String data) {
        try {
            File logFile = new File(path + "/" + logName + ".log"); // Create the file with .log format
            logFile.createNewFile(); // Create the file if not exist
            WriteOnLog(logFile, data); // Function to write the message to it
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
        }
    }

    // Function to write to the file
    public static void WriteOnLog(File logFile, String data) {
        try {
            FileWriter myWriter = new FileWriter(logFile, true); // Create the writer that doesn't overwrite existing content in the file
            myWriter.write(data+"\n"); // Write the message
            myWriter.close(); // Close the writer
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
        }
    }
}
