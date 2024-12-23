import java.util.*;
class Database {
    public static List<Category> categories = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<Customer> customers = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<Admin> admins = new ArrayList<>();

    static {

        categories.add(new Category(1, "Electronics", "Devices like phones, laptops, etc."));
        categories.add(new Category(2, "Accessories", "like headsets , covers and cable wires"));
        categories.add(new Category(3, "Home Appliances", "Appliances for home use"));


        products.add(new Product(1, "Smartphone", "Latest model smartphone", 799.99, 10, categories.get(0)));
        products.add(new Product(2, "Laptop", "High-end gaming laptop", 1499.99, 5, categories.get(0)));
        products.add(new Product(3, "T-shirt", "Comfortable cotton t-shirt", 19.99, 50, categories.get(1)));


        customers.add(new Customer("Peter", "password123", new Date(), 500.00, "1234 Elm St", Gender.MALE, Arrays.asList("Technology", "Gaming")));
        customers.add(new Customer("Malek", "password123", new Date(), 300.00, "5678 Oak St", Gender.FEMALE, Arrays.asList("Fashion", "Travel")));


        admins.add(new Admin("admin1", "adminpass123", new Date(), "Manager", 40));
        admins.add(new Admin("admin2", "adminpass456", new Date(), "Supervisor", 35));
    }
}