package DependencyInjectionExample;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public Customer findCustomerById(int id) {
        // For demonstration, return a dummy customer
        return new Customer(id, "John Doe");
    }
}
