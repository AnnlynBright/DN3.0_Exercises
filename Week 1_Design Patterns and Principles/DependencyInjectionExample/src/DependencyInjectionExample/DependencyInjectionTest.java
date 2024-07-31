package DependencyInjectionExample;

public class DependencyInjectionTest {

    public static void main(String[] args) {
        // Create an instance of CustomerRepository
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        
        // Inject CustomerRepository into CustomerService
        CustomerService customerService = new CustomerService(customerRepository);
        
        // Use the service to find a customer
        Customer customer = customerService.getCustomerById(1);
        System.out.println("Customer Found: " + customer);
    }
}

