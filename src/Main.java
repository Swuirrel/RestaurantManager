import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the restaurant application! Please enter the restaurant manager's name to start: ");
        String managerName = scanner.nextLine();
        RestaurantManager restaurantManager = new RestaurantManager(managerName);

        while (true) {
            try {
                System.out.println("\n------------ Restaurant Application -----------");
                System.out.println("Choose an option:");
                System.out.println("1 - Manager");
                System.out.println("2 - Client");
                System.out.println("3 - Exit");

                int userOption = scanner.nextInt();
                scanner.nextLine();

                switch (userOption) {
                    case 1 -> {
                        restaurantManager.greet();
                        manageAsManager(scanner, restaurantManager);
                    }
                    case 2 -> {
                        System.out.println("Welcome to the client side! Before we begin, please enter your name: ");
                        String clientName = scanner.nextLine();
                        Client client = new Client(clientName);
                        client.greet();
                        manageAsClient(scanner, client, restaurantManager);
                    }
                    case 3 -> {
                        System.out.println("Exiting the program...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void manageAsManager(Scanner scanner, RestaurantManager restaurantManager) {
        while (true) {
            try {
                System.out.println("\n------------ "+ restaurantManager.name + "'s Restaurant Manager ------------");
                System.out.println("What would you like to do?");
                System.out.println("1 - Display Menus, 2 - Add a Menu, 3 - Remove a Menu, 4 - Edit a Menu, 5 - Back");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (option) {
                    case 1 -> restaurantManager.viewItems();
                    case 2 -> restaurantManager.addMenu();
                    case 3 -> restaurantManager.removeMenu();
                    case 4 -> restaurantManager.editMenu();
                    case 5 -> {
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void manageAsClient(Scanner scanner, Client client, RestaurantManager restaurantManager) {
        while (true) {
            try {
                System.out.println("\n------------ "+ client.name + "'s Food Ordering Application ------------");
                System.out.println("What would you like to do?");
                System.out.println("1 - View a Menu, 2 - Add to Cart, 3 - Remove from Cart, 4 - View Cart, 5 - Pay, 6 - Back");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (option) {
                    case 1 -> restaurantManager.viewItems();
                    case 2 -> client.addToOrder(restaurantManager);
                    case 3 -> client.removeFromOrder();
                    case 4 -> client.viewItems();
                    case 5 -> client.pay();
                    case 6 -> {
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}


abstract class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    abstract void viewItems();
    public void greet() {
        System.out.println("Hello, " + name + "!");
    }
}


interface OrderManagement {
    void addToOrder(RestaurantManager restaurantManager);

    double calculateTotalPrice();
}



class Dish {

    private final String name;
    private final String type;
    private final double price;

    public Dish(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
