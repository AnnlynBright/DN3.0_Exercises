import java.util.*;

class Product {
    private String productId;
    private String productName;
    private String category;

    public Product(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

class SearchAlgorithms {
    public static Product linearSearch(Product[] products, String target) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(target)) {
                return product;
            }
        }
        return null;
    }

    public static Product binarySearch(Product[] products, String target) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].getProductName().compareToIgnoreCase(target);

            if (comparison == 0) {
                return products[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}

public class ECommerceSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of products: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Product[] products = new Product[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for product " + (i + 1) + ":");
            System.out.print("Product ID: ");
            String productId = scanner.nextLine();
            System.out.print("Product Name: ");
            String productName = scanner.nextLine();
            System.out.print("Category: ");
            String category = scanner.nextLine();

            products[i] = new Product(productId, productName, category);
        }

        System.out.print("\nEnter the product name to search: ");
        String target = scanner.nextLine();

        System.out.println("\nLinear Search:");
        Product foundProductLinear = SearchAlgorithms.linearSearch(products, target);
        if (foundProductLinear != null) {
            System.out.println("Product found: " + foundProductLinear);
        } else {
            System.out.println("Product not found.");
        }

        Arrays.sort(products, Comparator.comparing(Product::getProductName));

        System.out.println("\nBinary Search:");
        Product foundProductBinary = SearchAlgorithms.binarySearch(products, target);
        if (foundProductBinary != null) {
            System.out.println("Product found: " + foundProductBinary);
        } else {
            System.out.println("Product not found.");
        }

        scanner.close();
    }
}
