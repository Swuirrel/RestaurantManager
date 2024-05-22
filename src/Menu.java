import java.util.*;

public class Menu {
    String restaurantName;
    ArrayList<Dish> dishes;

    public Menu(String restaurantName) {
        this.restaurantName = restaurantName;
        this.dishes = new ArrayList<>();
    }

    void addDish() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the name of the new dish:");
            String dishName = scanner.nextLine();

            System.out.println("Enter the price of the new dish:");
            double dishPrice = scanner.nextDouble();
            scanner.nextLine();

            String dishType;
            while (true) {
                System.out.println("Enter the type of the new dish (appetizer, meal, dessert):");
                dishType = scanner.nextLine().toLowerCase();

                if (dishType.equals("appetizer") || dishType.equals("meal") || dishType.equals("dessert")) {
                    break;
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
                System.out.print(count++ + " - " + dish.getName() + " ");
            }
            System.out.println("\nWhich dish would you like to remove?");
            int dishToRemove = scanner.nextInt();
            scanner.nextLine();
            if (dishToRemove > 0 && dishToRemove <= dishes.size()) {
                String dishToRemoveName = dishes.get(dishToRemove - 1).getName();
                dishes.remove(dishToRemove - 1);
                System.out.println(dishToRemoveName + " was removed successfully!");
            } else {
                System.out.println("Invalid input.");
                break;
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
}

