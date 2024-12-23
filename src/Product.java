class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private Category category;

    public Product(int id, String name, String description, double price, int stock, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Category getCategory() {
        return category;
    }

    public boolean reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    public void displayProduct() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock);
        System.out.println("Category: " + category.getName());
    }


}