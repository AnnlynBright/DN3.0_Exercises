import java.util.*;

class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

class InventoryManagementSystem {
    private HashMap<String, Product> inventory;

    public InventoryManagementSystem() {
        this.inventory = new HashMap<>();
    }

    public void addProduct(Product product) {
        inventory.put(product.getProductId(), product);
    }

    public void updateProduct(Product product) {
        if (inventory.containsKey(product.getProductId())) {
            inventory.put(product.getProductId(), product);
        } else {
            System.out.println("Product not found!");
        }
    }

    public void deleteProduct(String productId) {
        if (inventory.containsKey(productId)) {
            inventory.remove(productId);
        } else {
            System.out.println("Product not found!");
        }
    }

    public Product getProduct(String productId) {
        return inventory.get(productId);
    }

    public void listAllProducts() {
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nInventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Product");
            System.out.println("5. List All Products");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    String addId = scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String addName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int addQuantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double addPrice = scanner.nextDouble();
                    scanner.nextLine();

                    Product newProduct = new Product(addId, addName, addQuantity, addPrice);
                    ims.addProduct(newProduct);
                    System.out.println("Product added successfully!");
                    break;
                case 2:
                    System.out.print("Enter product ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new product name: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int updateQuantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    double updatePrice = scanner.nextDouble();
                    scanner.nextLine();

                    Product updateProduct = new Product(updateId, updateName, updateQuantity, updatePrice);
                    ims.updateProduct(updateProduct);
                    break;
                case 3:
                    System.out.print("Enter product ID to delete: ");
                    String deleteId = scanner.nextLine();
                    ims.deleteProduct(deleteId);
                    break;
                case 4:
                    System.out.print("Enter product ID to view: ");
                    String viewId = scanner.nextLine();
                    Product product = ims.getProduct(viewId);
                    if (product != null) {
                        System.out.println(product);
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;
                case 5:
                    System.out.println("All products:");
                    ims.listAllProducts();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }
}
