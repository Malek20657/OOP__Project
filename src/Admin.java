import java.util.*;
class Admin extends User {
    private String role;
    private int workingHours;

    public Admin(String username, String password, Date dateOfBirth, String role, int workingHours) {
        super(username, password, dateOfBirth);
        this.role = role;
        this.workingHours = workingHours;
    }

    public void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. Show All Entities");
            System.out.println("2. Add New Product");
            System.out.println("3. Log Out");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showEntities(Database.customers, Database.products, Database.orders);
                    break;

                case 2:
                    addProduct(scanner);
                    break;

                case 3:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addProduct(Scanner scanner) {
        System.out.println("\n---- Add Product ----");
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Product Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        Category category = Database.categories.stream()
                .filter(c -> c.getCategoryId() == categoryId)
                .findFirst()
                .orElse(null);

        if (category != null) {
            Product product = new Product(Database.products.size() + 1, name, description, price, stock, category);
            Database.products.add(product);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Invalid category ID.");
        }
    }

    private void showEntities(List<Customer> customers, List<Product> products, List<Order> orders) {
        System.out.println("\n---- All Customers ----");
        customers.forEach(customer -> System.out.println(customer.getUsername()));

        System.out.println("\n---- All Products ----");
        products.forEach(product -> System.out.println(product.getName()));

        System.out.println("\n---- All Orders ----");
        orders.forEach(order -> System.out.println("Order ID: " + order.getOrderId()));
    }
}