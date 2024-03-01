//Necessary Imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;

public class KeepRealTimeData implements Runnable {
    static double TimeToKeepInHours = GetData.TimeToKeepInHours; // Time difference in hours between the last message and the first, retrieved from the GetData Class
    static File file = GetData.file; // Retrieves the file from GetData Class
    static LocalDateTime DateNow; // Current date
    static LocalDateTime DateOfMessage; // Message date
    @SuppressWarnings("deprecation")
    @Override
    // Main function
    // Here, check if the file is not empty and read the first and last line to compare the time difference
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (file.length() > 0) { // Check if the file is not empty
                try {
                    BufferedReader readFirstLine = new BufferedReader(new FileReader(file)); // Function to read the first line of the file
					ReversedLinesFileReader readLastLine = new ReversedLinesFileReader(file); // Function to read the last line of the file
                    String firstLine = readFirstLine.readLine(); // Read the first line
                    String lastLine = readLastLine.readLine(); // Read the last line
                    if (!firstLine.equals(lastLine)) { // Compare the lines to check if the file doesn't have only one line
                    	splitlines(lastLine, firstLine); // Function to compare the time difference between the dates
                    }
                    readFirstLine.close(); // Close the reader for the first line
                    readLastLine.close(); // Close the reader for the last line
                } catch (IOException e ) {
                	System.out.println(e.getMessage());
                }
            }
        }
    }

    // Function to extract the message date from the file
    // Here, we receive the date of the first and last messages and compare them
    public static void splitlines(String LastLine, String FirstLine) {
        String[] lastLineTime = LastLine.split(" "); // Split the information from the last line
        String[] firstLineTime = FirstLine.split(" "); // Split the information from the first line
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formatter for the date
        DateNow = LocalDateTime.parse((lastLineTime[0] + " " + lastLineTime[1]), formatter); // Retrieve the date and convert it using the formatter
        DateOfMessage = LocalDateTime.parse((firstLineTime[0] + " " + firstLineTime[1]), formatter); // Retrieve the date and convert it using the formatter
        double diffHours = Duration.between(DateOfMessage, DateNow).toHours(); // Calculate the time difference between them in hours
        if (diffHours > TimeToKeepInHours) { // Check if it meets the requirements to delete the old information
            clearFirstLine(); // Function to delete the first line of the file
        }
    }


    // Function to delete the first line of the file
    public static void clearFirstLine() {
        try {
            String fileContent = FileUtils.readFileToString(file, Charset.defaultCharset()); // Read the line 
            String newContent = fileContent.replaceFirst(".*\\r?\\n", ""); // New string in that case we remove the line
            FileUtils.writeStringToFile(file, newContent, Charset.defaultCharset()); // Rewrite the file 
        } catch (IOException e) {
        	System.out.println(e.getMessage()); // In case of error, display the error message
        }
    }

}

