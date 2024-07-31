import java.util.Scanner;

class Order {
    private String orderId;
    private String customerName;
    private double totalPrice;

    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

public class SortOrders {
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);
            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;
        return i + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of orders: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Order[] orders = new Order[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for order " + (i + 1) + ":");
            System.out.print("Order ID: ");
            String orderId = scanner.nextLine();
            System.out.print("Customer Name: ");
            String customerName = scanner.nextLine();
            System.out.print("Total Price: ");
            double totalPrice = scanner.nextDouble();
            scanner.nextLine();
            orders[i] = new Order(orderId, customerName, totalPrice);
        }

        System.out.println("Choose sorting algorithm:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Quick Sort");
        int choice = scanner.nextInt();

        if (choice == 1) {
            bubbleSort(orders);
            System.out.println("Orders sorted using Bubble Sort:");
        } else if (choice == 2) {
            quickSort(orders, 0, orders.length - 1);
            System.out.println("Orders sorted using Quick Sort:");
        } else {
            System.out.println("Invalid choice");
        }

        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
