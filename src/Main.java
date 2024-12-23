import java.util.*;
enum Gender {
    MALE, FEMALE
}

enum PaymentMethod {
    CREDIT_CARD, FAWRY, CASH_ON_DELIVERY
}

enum OrderStatus {
    PENDING, COMPLETED, CANCELED
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n---- Main Menu ----");
            System.out.println("1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customerLogin(scanner);
                    break;

                case 2:
                    adminLogin(scanner);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void customerLogin(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Customer customer = findCustomer(username, password);
        if (customer != null) {
            customer.customerMenu(scanner);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void adminLogin(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Admin admin = findAdmin(username, password);
        if (admin != null) {
            admin.adminMenu(scanner);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static Customer findCustomer(String username, String password) {
        for (Customer customer : Database.customers) {
            if (customer.getUsername().equals(username) && customer.password.equals(password)) {
                return customer;
            }
        }
        return null;
    }

    private static Admin findAdmin(String username, String password) {
        for (Admin admin : Database.admins) {
            if (admin.getUsername().equals(username) && admin.password.equals(password)) {
                return admin;
            }
        }
        return null;
    }
}
