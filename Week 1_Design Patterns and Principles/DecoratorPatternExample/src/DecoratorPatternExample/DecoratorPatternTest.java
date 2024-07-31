package DecoratorPatternExample;

public class DecoratorPatternTest {

    public static void main(String[] args) {
        // Create a base email notifier
        Notifier emailNotifier = new EmailNotifier();

        // Add SMS functionality using decorator
        Notifier smsEmailNotifier = new SMSNotifierDecorator(emailNotifier);
        
        // Add Slack functionality using decorator
        Notifier slackSMSNotifier = new SlackNotifierDecorator(smsEmailNotifier);

        // Send notification via email, SMS, and Slack
        slackSMSNotifier.send("Hello, this is a test message.");
    }
}

