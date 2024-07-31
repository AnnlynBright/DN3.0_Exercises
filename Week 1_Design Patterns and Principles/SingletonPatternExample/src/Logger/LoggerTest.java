package Logger;


public class LoggerTest {

    public static void main(String[] args) {
        // Get the single instance of Logger
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Test to see if both references point to the same instance
        if (logger1 == logger2) {
            System.out.println("Logger instances are the same.");
        } else {
            System.out.println("Logger instances are different.");
        }

        // Using the logger to log a message
        logger1.log("This is a test message.");

        // Output the log message from the second reference
        logger2.log("This is another test message.");
    }
}
