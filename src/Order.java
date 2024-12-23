class Order {
    private int orderId;
    private int customerId;
    private Cart cart;
    private PaymentMethod paymentMethod;
    private double totalAmount;
    private OrderStatus status;

    public Order(int orderId, PaymentMethod paymentMethod, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cart = cart;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return Database.customers.get(customerId - 1);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}