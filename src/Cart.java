import java.util.*;
class Cart {
    private int cartId;
    private int customerId;
    private Map<Product, Integer> items;

    public Cart(int cartId, int customerId) {
        this.cartId = cartId;
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public void displayCart() {
        System.out.println("---- Cart Items ----");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product + " | Quantity: " + quantity);
        }
    }

    public Order placeOrder(Customer customer, PaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Please add items to cart.");
            return null;
        }

        double totalAmount = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += product.getPrice() * quantity;
        }

        Order order = new Order(Database.orders.size() + 1, paymentMethod, totalAmount);
        Database.orders.add(order);



        items.clear();

        return order;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}