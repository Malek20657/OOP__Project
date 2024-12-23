import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Stack;

public class ECommerceApp extends Application {
    private Stage primaryStage;
    private Stack<Scene> sceneStack = new Stack<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        showMainMenu();

        primaryStage.setTitle("E-Commerce App");
        primaryStage.show();
    }

    private void showMainMenu() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("E-Commerce App");
        Button adminButton = new Button("Admin Login");
        Button customerButton = new Button("Customer Login");

        adminButton.setOnAction(e -> showLoginMenu(true));
        customerButton.setOnAction(e -> showLoginMenu(false));

        mainLayout.getChildren().addAll(titleLabel, adminButton, customerButton);

        Scene mainScene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(mainScene);
    }

    private void showLoginMenu(boolean isAdmin) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));

        Label titleLabel = new Label(isAdmin ? "Admin Login" : "Customer Login");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> handleLogin(isAdmin, usernameField.getText(), passwordField.getText()));

        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);

        Scene loginScene = new Scene(loginLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(loginScene);
    }

    private void handleLogin(boolean isAdmin, String username, String password) {
        if (isAdmin) {
            for (Admin admin : Database.admins) {
                if (admin.username.equals(username) && admin.password.equals(password)) {
                    System.out.println("Logged in as Admin.");
                    showAdminMenu(admin);
                    return;
                }
            }
        } else {
            for (Customer customer : Database.customers) {
                if (customer.username.equals(username) && customer.password.equals(password)) {
                    System.out.println("Logged in as Customer.");
                    showCustomerMenu(customer);
                    return;
                }
            }
        }
        System.out.println("Invalid username or password. Try again.");
    }

    private void showAdminMenu(Admin admin) {
        VBox adminLayout = new VBox(10);
        adminLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Admin Menu");
        Button viewProductsButton = new Button("View Products");
        Button addProductButton = new Button("Add Product");
        Button logoutButton = new Button("Logout");

        viewProductsButton.setOnAction(e -> showProductsScene());
        addProductButton.setOnAction(e -> showAddProductScene());
        logoutButton.setOnAction(e -> showMainMenu());

        adminLayout.getChildren().addAll(titleLabel, viewProductsButton, addProductButton, logoutButton);

        Scene adminScene = new Scene(adminLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(adminScene);
    }

    private void showCustomerMenu(Customer customer) {
        VBox customerLayout = new VBox(10);
        customerLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Customer Menu");
        Button viewProductsButton = new Button("View Products");
        Button viewCartButton = new Button("View Cart");
        Button logoutButton = new Button("Logout");

        viewProductsButton.setOnAction(e -> showProductsScene(customer));
        viewCartButton.setOnAction(e -> showCartScene(customer));
        logoutButton.setOnAction(e -> showMainMenu());

        customerLayout.getChildren().addAll(titleLabel, viewProductsButton, viewCartButton, logoutButton);

        Scene customerScene = new Scene(customerLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(customerScene);
    }

    private void showProductsScene() {
        VBox productsLayout = new VBox(10);
        productsLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Products");
        ListView<String> productsList = new ListView<>();
        Label productDetails = new Label("Select a product to view details");

        for (Product product : Database.products) {
            productsList.getItems().add(product.getName());
        }

        productsList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Product selectedProduct = Database.products.stream()
                        .filter(product -> product.getName().equals(newSelection))
                        .findFirst()
                        .orElse(null);

                if (selectedProduct != null) {
                    productDetails.setText(String.format("Name: %s\nPrice: %.2f\nDescription: %s",
                            selectedProduct.getName(),
                            selectedProduct.getPrice(),
                            selectedProduct.getDescription()));
                }
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            if (!sceneStack.isEmpty()) {
                primaryStage.setScene(sceneStack.pop());
            }
        });

        productsLayout.getChildren().addAll(titleLabel, productsList, productDetails, backButton);

        Scene productsScene = new Scene(productsLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(productsScene);
    }

    private void showProductsScene(Customer customer) {
        VBox productsLayout = new VBox(10);
        productsLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Products");
        ListView<String> productsList = new ListView<>();
        Label productDetails = new Label("Select a product to view details");
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setDisable(true);

        for (Product product : Database.products) {
            productsList.getItems().add(product.getName());
        }

        productsList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            addToCartButton.setDisable(newSelection == null);

            if (newSelection != null) {
                Product selectedProduct = Database.products.stream()
                        .filter(product -> product.getName().equals(newSelection))
                        .findFirst()
                        .orElse(null);

                if (selectedProduct != null) {
                    productDetails.setText(String.format("Name: %s\nPrice: %.2f\nDescription: %s",
                            selectedProduct.getName(),
                            selectedProduct.getPrice(),
                            selectedProduct.getDescription()));
                }
            }
        });

        addToCartButton.setOnAction(e -> {
            String selectedProductName = productsList.getSelectionModel().getSelectedItem();
            Product selectedProduct = Database.products.stream()
                    .filter(product -> product.getName().equals(selectedProductName))
                    .findFirst()
                    .orElse(null);

            if (selectedProduct != null) {
                customer.getCart().addItem(selectedProduct, 1);
                System.out.println("Product added to cart!");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            if (!sceneStack.isEmpty()) {
                primaryStage.setScene(sceneStack.pop());
            }
        });

        productsLayout.getChildren().addAll(titleLabel, productsList, productDetails, addToCartButton, backButton);

        Scene productsScene = new Scene(productsLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(productsScene);
    }

    private void showCartScene(Customer customer) {
        VBox cartLayout = new VBox(10);
        cartLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Your Cart");
        ListView<String> cartList = new ListView<>();

        customer.getCart().getItems().forEach((product, quantity) -> {
            cartList.getItems().add(String.format("%s (x%d) - %.2f", product.getName(), quantity, product.getPrice()));
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            if (!sceneStack.isEmpty()) {
                primaryStage.setScene(sceneStack.pop());
            }
        });

        cartLayout.getChildren().addAll(titleLabel, cartList, backButton);

        Scene cartScene = new Scene(cartLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(cartScene);
    }

    private void showAddProductScene() {
        VBox addProductLayout = new VBox(10);
        addProductLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Add Product");
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");

        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            double price;
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Invalid price format!");
                alert.show();
                return;
            }
            String description = descriptionArea.getText();

            if (name.isEmpty() || description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please fill in all fields.");
                alert.show();
                return;
            }

            Database.products.add(new Product(Database.products.size() + 1, name, description, price, 10, Database.categories.get(0)));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Product added successfully!");
            alert.show();
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            if (!sceneStack.isEmpty()) {
                primaryStage.setScene(sceneStack.pop());
            }
        });

        addProductLayout.getChildren().addAll(titleLabel, nameField, priceField, descriptionArea, addButton, backButton);

        Scene addProductScene = new Scene(addProductLayout, 400, 300);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(addProductScene);
    }
}