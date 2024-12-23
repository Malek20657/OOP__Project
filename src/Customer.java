import java.util.*;
class Customer extends User {
    private double balance;
    private String address;
    private Gender gender;
    private List<String> interests;
    private Cart cart;

    public Customer(String username, String password, Date dateOfBirth, double balance, String address, Gender gender, List<String> interests) {
        super(username, password, dateOfBirth);
        this.balance = balance;
        this.address = address;
        this.gender = gender;
        this.interests = interests;
        this.cart = new Cart(Database.customers.indexOf(this) + 1, Database.customers.indexOf(this));
    }

    public void customerMenu(Scanner scanner) {
        Cart cart = new Cart(Database.customers.indexOf(this) + 1, Database.customers.indexOf(this));

        while (true) {
            System.out.println("\n---- Customer Menu ----");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Log Out");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewProducts();
                    break;

                case 2:
                    addToCart(scanner, cart);
                    break;

                case 3:
                    cart.displayCart();
                    break;

                case 4:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewProducts() {
        System.out.println("\n---- Products ----");
        for (Product product : Database.products) {
            product.displayProduct();
        }
    }

    private void addToCart(Scanner scanner, Cart cart) {
        System.out.println("\n---- Add to Cart ----");
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (productId <= 0 || productId > Database.products.size()) {
            System.out.println("Invalid Product ID.");
            return;
        }

        Product product = Database.products.get(productId - 1);
        if (product.reduceStock(quantity)) {
            cart.addItem(product, quantity);
            System.out.println("Product added to cart!");
        } else {
            System.out.println("Insufficient stock.");
        }
    }

    public Cart getCart() {
        return this.cart; 
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }
}