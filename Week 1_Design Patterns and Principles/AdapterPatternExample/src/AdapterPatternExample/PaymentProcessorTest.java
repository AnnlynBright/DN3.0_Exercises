package AdapterPatternExample;

public class PaymentProcessorTest {

    public static void main(String[] args) {
        // Create payment gateway instances
        PayPalGateway payPalGateway = new PayPalGateway();
        StripeGateway stripeGateway = new StripeGateway();

        // Create adapters for the payment gateways
        PaymentProcessor payPalProcessor = new PayPalAdapter(payPalGateway);
        PaymentProcessor stripeProcessor = new StripeAdapter(stripeGateway);

        // Process payments using the adapters
        payPalProcessor.processPayment(100.00);
        stripeProcessor.processPayment(200.00);
    }
}
