package Logger;


public class Logger {

    // Step 2: Create a private static instance of the Logger class
    private static Logger instance;

    // Step 2: Private constructor to prevent instantiation
    private Logger() {
        // Private constructor
    }

    // Step 3: Provide a public static method to get the instance of the Logger class
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Example method to demonstrate logging
    public void log(String message) {
        System.out.println("Log: " + message);
    }
}
