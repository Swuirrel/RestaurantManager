import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a restaurant manager or a client? (type 'M' or 'C')");
        String choice = scanner.nextLine();
        String userType;
        if (choice.equalsIgnoreCase("M")) {
            userType = "manager";
        } else {
            userType = "client";
        }
        System.out.println("Welcome to the " + userType + " side! Before we begin, please enter your name: ");
        String name = scanner.nextLine();
        while (true) {
            try {
                if (choice.equalsIgnoreCase("M")) {
                    RestaurantManager restaurantManager = new RestaurantManager(name);
                    System.out.println("Hi " + name + "! What would you like to do today?");
                    while (true) {
                        System.out.println("1 - Display Menus, 2 - Add a Menu, 3 - Remove a Menu, 4 - Edit a Menu, 5 - Exit");
                        int option = scanner.nextInt();
                        switch (option) {
                            case 1 -> restaurantManager.displayMenu();
                            case 2 -> restaurantManager.addMenu();
                            case 3 -> restaurantManager.removeMenu();
                            case 4 -> restaurantManager.editMenu();
                            case 5 -> {
                                return;
                            }
                            default -> System.out.println("Invalid option. Please try again.");
                        }
                    }
                } else if (choice.equalsIgnoreCase("C")) {
                    Client client = new Client(name);
                    System.out.println("Hi " + name + "! What would you like to do today?");
                    while (true) {
                        System.out.println("1 - View a Menu, 2 - Add to Cart, 3 - Remove from Cart, 4 - View Cart, 5 - Pay, 6 - Exit");
                        int option = scanner.nextInt();
                        switch (option) {
                            case 6:
                                return;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid choice. Please enter 'M' for manager or 'C' for client.");
                    choice = scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

}

abstract class User {
    String name;
    abstract void displayMenu();
}

interface OrderManagement {
    void addToOrder(Dish dish);
    int calculateTotalPrice();
}

class RestaurantManager extends User {
    String name;
    ArrayList<Menu> menus;
    public RestaurantManager(String name, ArrayList<Menu> menus) {
        this.name = name;
        this.menus = menus;
    }
    public RestaurantManager(String name) {
        this.name = name;
        this.menus = new ArrayList<>();
    }
    @Override
    void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        if (menus.isEmpty()) {
            System.out.println("No menus available.");
            return;
        }
        int count = 1;
        for (Menu menu : menus) {
            System.out.print(count++ + " - " + menu.restaurantName + " ");
        }
        System.out.println("\nWhich menu would you like to view?");
        int choice = scanner.nextInt();
        if (menus.get(choice - 1).dishes.isEmpty()) {
            System.out.println("This menu is empty.");
            return;
        }
        if (choice > 0 && choice <= menus.size()) {
            System.out.println("How would you like the menu sorted? (1 - By type, 2 - By price, 3 - By Name)");
            int sortChoice = scanner.nextInt();
            if (sortChoice > 0 && sortChoice <= 3) {
                switch (sortChoice) {
                    case 1 -> printByType(choice);
                    case 2 -> printByPrice(choice);
                    case 3 -> printByName(choice);
                }
            }
        } else {
            System.out.println("Invalid input. ");
        }
    }
    void addMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Okay! Please enter the name of the restaurant for the new menu: ");
        String restaurantName = scanner.nextLine();
        menus.add(new Menu(restaurantName));
        System.out.println("Would you like to add dishes to your new menu? (Type 'y' to continue or any other key to go back)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            menus.get(findMenu(restaurantName)).addDish();
        }
    }
    void editMenu() {
        if (menus.isEmpty()) {
            System.out.println("No menus available.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        for (Menu menu : menus) {
            System.out.print(count++ + " - " + menu.restaurantName + " ");
        }
        System.out.println("\nWhich menu would you like to edit?");
        int selection = scanner.nextInt();
        if (selection > 0 && selection <= menus.size()) {
            Menu menu = menus.get(selection - 1);
            while (true) {
                System.out.println("What would you like to do with the menu?");
                System.out.println("1 - Add a dish");
                System.out.println("2 - Remove a dish");
                System.out.println("3 - Done editing");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> menu.addDish();
                    case 2 -> menu.removeDish();
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid input.");
        }

    }

    int findMenu(String menuName) {
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).restaurantName.equalsIgnoreCase(menuName)) {
                return i;
            }
        }
        return -1;
    }
    void removeMenu() {
        Scanner scanner = new Scanner(System.in);
        if (menus.isEmpty()) {
            System.out.println("No menus available.");
            return;
        }
        int count = 1;
        for (Menu menu : menus) {
            System.out.print(count++ + " - " + menu.restaurantName + " ");
        }
        System.out.println("\nWhich menu would you like to remove?");
        int selection = scanner.nextInt();
        String toBeRemovedName = menus.get(selection -1).restaurantName;
        if (selection > 0 && selection <= menus.size()) {
            menus.remove(selection - 1);
            System.out.println(toBeRemovedName + " was removed successfully!");
        } else {
            System.out.println("Invalid input. ");
        }
    }
    void printByType(int menuIndex) {
        Menu menuChoice = menus.get(menuIndex - 1);
        menuChoice.dishes.sort(new TypeComparator());
        System.out.println("         " + menuChoice.restaurantName + " Menu (By Type)");
        System.out.println("-------------------------------");
        System.out.println("Appetizers: ");
        for (Dish dish : menuChoice.dishes) {
            if (dish.type.equals("appetizer")) {
                System.out.println("- " + dish.name + ", $" +  String.format("%.2f", dish.price));
            }
        }
        System.out.println("Meals: ");
        for (Dish dish : menuChoice.dishes) {
            if (dish.type.equals("meal")) {
                System.out.println("- " + dish.name + ", $" +  String.format("%.2f", dish.price));
            }
        }
        System.out.println("Desserts: ");
        for (Dish dish : menuChoice.dishes) {
            if (dish.type.equals("dessert")) {
                System.out.println("- " + dish.name + ", $" +  String.format("%.2f", dish.price));
            }
        }
    }
    void printByPrice(int menuIndex) {
        Menu menuChoice = menus.get(menuIndex - 1);
        menuChoice.dishes.sort(new PriceComparator());
        System.out.println("         " + menuChoice.restaurantName + " Menu (By Price)");
        System.out.println("-------------------------------");
        for (Dish dish : menuChoice.dishes) {
            System.out.println("- " + dish.name + ", $" +  String.format("%.2f", dish.price));
        }
    }
    void printByName(int menuIndex) {
        Menu menuChoice = menus.get(menuIndex - 1);
        menuChoice.dishes.sort(new NameComparator());
        System.out.println("         " + menuChoice.restaurantName + " Menu (By Name)");
        System.out.println("-------------------------------");
        for (Dish dish : menuChoice.dishes) {
            System.out.println("- " + dish.name + ", $" +  String.format("%.2f", dish.price));
        }
    }
}
class Client extends User implements OrderManagement {
    String name;
    ArrayList<Dish> cart;

    public Client(String name, ArrayList<Dish> cart) {
        this.name = name;
        this.cart = cart;
    }
    public Client(String name) {
        this.name = name;
        this.cart = new ArrayList<>();
    }

    @Override
    void displayMenu() {

    }

    @Override
    public void addToOrder(Dish dish) {

    }
    public void removeFromOrder(Dish dish) {

    }
    void viewOrder() {

    }
    void pay() {

    }

    @Override
    public int calculateTotalPrice() {
        return 0;
    }
}
class Menu {
    String restaurantName;
    ArrayList<Dish> dishes;

    public Menu(String restaurantName, ArrayList<Dish> dishes) {
        this.restaurantName = restaurantName;
        this.dishes = dishes;
    }
    public Menu(String restaurantName) {
        this.restaurantName = restaurantName;
        this.dishes = new ArrayList<>();
    }

    void addDish() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the name of the dish:");
            String dishName = scanner.nextLine();

            System.out.println("Enter the price of the dish:");
            double dishPrice = scanner.nextDouble();
            scanner.nextLine();

            String dishType;
            while (true) {
                System.out.println("Enter the type of the dish (appetizer, meal, dessert):");
                dishType = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive comparison

                if (dishType.equals("appetizer") || dishType.equals("meal") || dishType.equals("dessert")) {
                    break; // Valid type, exit loop
                } else {
                    System.out.println("Invalid dish type. Please enter 'appetizer', 'meal', or 'dessert'.");
                }
            }

            dishes.add(new Dish(dishName, dishType, dishPrice));
            System.out.println(dishName + " added successfully to the menu!");

            System.out.println("Do you want to add another dish? (Type 'y' to continue or any other key to go back)");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    void removeDish() {
        Scanner scanner = new Scanner(System.in);
        if (dishes.isEmpty()) {
            System.out.println("No dishes available.");
            return;
        }
        while (true) {
            int count = 1;
            for (Dish dish : dishes) {
                System.out.print(count++ + " - " + dish.name + " ");
            }
            System.out.println("\nWhich dish would you like to remove?");
            int dishToRemove = scanner.nextInt();
            scanner.nextLine();
            if (dishToRemove > 0 && dishToRemove <= dishes.size()) {
                String dishToRemoveName = dishes.get(dishToRemove - 1).name;
                dishes.remove(dishToRemove - 1);
                System.out.println(dishToRemoveName + " was removed successfully!");
            } else {
                System.out.println("Invalid input.");
            }
            if (!dishes.isEmpty()) {
                System.out.println("Do you want to remove another dish? (Type 'y' to continue or any other key to go back)");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("y")) {
                    break;
                }
            } else {
                break;
            }

        }
    }

    void displayMenu() {

    }
    void dishCount() {

    }
}
class Dish {
    String name;
    String type;
    double price;

    public Dish(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }
}

class TypeComparator implements Comparator<Dish> {

    @Override
    public int compare(Dish o1, Dish o2) {
        String type1 = o1.type.toLowerCase();
        String type2 = o2.type.toLowerCase();
        int order1 = getOrder(type1);
        int order2 = getOrder(type2);
        return Integer.compare(order1, order2);
    }
    private int getOrder(String type) {
        type = type.toLowerCase();
        return switch (type) {
            case "appetizer" -> 1;
            case "meal" -> 2;
            case "dessert" -> 3;
            default -> 0;
        };
    }
}
class PriceComparator implements Comparator<Dish> {

    @Override
    public int compare(Dish o1, Dish o2) {
        return Double.compare(o1.price, o2.price);
    }
}
class NameComparator implements Comparator<Dish> {

    @Override
    public int compare(Dish o1, Dish o2) {
        return o1.name.compareTo(o2.name);
    }
}