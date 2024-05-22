import java.util.*;

public class RestaurantManager extends User {
    public ArrayList<Menu> menus;

    public RestaurantManager(String name) {
        super(name);
        this.menus = new ArrayList<>();
    }

    void viewItems() {
        Scanner scanner = new Scanner(System.in);
        try {
            if (menus.isEmpty()) {
                System.out.println("There are no menus to display.");
                return;
            }
            int count = 1;
            for (Menu menu : menus) {
                System.out.print(count++ + " - " + menu.restaurantName + " ");
            }
            System.out.println("\nWhich menu would you like to view?");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice > 0 && choice <= menus.size()) {
                if (menus.get(choice - 1).dishes.isEmpty()) {
                    System.out.println("This menu is empty.");
                    return;
                }
                System.out.println("How would you like the menu sorted? (1 - By type, 2 - By price, 3 - By Name)");
                int sortChoice = scanner.nextInt();
                scanner.nextLine();
                if (sortChoice > 0 && sortChoice <= 3) {
                    switch (sortChoice) {
                        case 1 -> printByType(choice);
                        case 2 -> printByPrice(choice);
                        case 3 -> printByName(choice);
                        default -> System.out.println("Invalid input.");
                    }
                }
            } else {
                System.out.println("Invalid input. ");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. ");
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }

    void addMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Okay! Please enter the name of the restaurant for the new menu: ");
            String restaurantName = scanner.nextLine();
            menus.add(new Menu(restaurantName));
            System.out.println("Would you like to add dishes to '" + restaurantName + "'? (Type 'y' to continue or any other key to go back)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                menus.get(findMenu(restaurantName)).addDish();
            }
        } catch (InputMismatchException e) {
            System.out.println("An error occurred. No dish added.");
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    void editMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            if (menus.isEmpty()) {
                System.out.println("There are no menus to edit.");
                return;
            }
            int count = 1;
            for (Menu menu : menus) {
                System.out.print(count++ + " - " + menu.restaurantName + " ");
            }
            System.out.println("\nWhich menu would you like to edit?");
            int selection = scanner.nextInt();
            scanner.nextLine();
            if (selection > 0 && selection <= menus.size()) {
                Menu menu = menus.get(selection - 1);
                while (true) {
                    System.out.println("\n------------ Menu Editor ------------");
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
                        default -> System.out.println("Invalid choice.");
                    }
                }
            } else {
                System.out.println("Invalid input.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Menu not updated.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
            System.out.println(e.getMessage());
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
        try {
            if (menus.isEmpty()) {
                System.out.println("There are no menus to remove.");
                return;
            }
            int count = 1;
            for (Menu menu : menus) {
                System.out.print(count++ + " - " + menu.restaurantName + " ");
            }
            System.out.println("\nWhich menu would you like to remove?");
            int selection = scanner.nextInt();
            scanner.nextLine();
            if (selection > 0 && selection <= menus.size()) {
                String toBeRemovedName = menus.get(selection - 1).restaurantName;
                menus.remove(selection - 1);
                System.out.println(toBeRemovedName + " was removed successfully!");
            } else {
                System.out.println("Invalid input. No menu removed.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. No dish removed.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
            System.out.println(e.getMessage());
        }
    }

    void printByType(int menuIndex) {
        Menu menuChoice = menus.get(menuIndex - 1);
        System.out.println("         " + menuChoice.restaurantName + " Menu (By Type)");
        System.out.println("-------------------------------");
        System.out.println("Appetizers: ");
        for (Dish dish : menuChoice.dishes) {
            if (dish.getType().equals("appetizer")) {
                System.out.println("- " + dish.getName() + ", $" + String.format("%.2f", dish.getPrice()));
            }
        }
        System.out.println("Meals: ");
        for (Dish dish : menuChoice.dishes) {
            if (dish.getType().equals("meal")) {
                System.out.println("- " + dish.getName() + ", $" + String.format("%.2f", dish.getPrice()));
            }
        }
        System.out.println("Desserts: ");
        for (Dish dish : menuChoice.dishes) {
            if (dish.getType().equals("dessert")) {
                System.out.println("- " + dish.getName() + ", $" + String.format("%.2f", dish.getPrice()));
            }
        }
    }

    void printByPrice(int menuIndex) {
        Menu menuChoice = menus.get(menuIndex - 1);
        bubbleSortByPrice(menuChoice.dishes);
        System.out.println("         " + menuChoice.restaurantName + " Menu (By Price)");
        System.out.println("-------------------------------");
        for (Dish dish : menuChoice.dishes) {
            System.out.println("- " + dish.getName() + ", $" + String.format("%.2f", dish.getPrice()));
        }
    }

    void bubbleSortByPrice(ArrayList<Dish> dishes) {
        int n = dishes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (dishes.get(j).getPrice() > dishes.get(j + 1).getPrice()) {
                    Dish temp = dishes.get(j);
                    dishes.set(j, dishes.get(j + 1));
                    dishes.set(j + 1, temp);
                }
            }
        }
    }


    void printByName(int menuIndex) {
        Menu menuChoice = menus.get(menuIndex - 1);
        quickSortByName(menuChoice.dishes, 0, menuChoice.dishes.size() - 1);
        System.out.println("         " + menuChoice.restaurantName + " Menu (By Name)");
        System.out.println("-------------------------------");
        for (Dish dish : menuChoice.dishes) {
            System.out.println("- " + dish.getName() + ", $" + String.format("%.2f", dish.getPrice()));
        }
    }

    void quickSortByName(ArrayList<Dish> dishes, int low, int high) {
        if (low < high) {
            int pi = partition(dishes, low, high);
            quickSortByName(dishes, low, pi - 1);
            quickSortByName(dishes, pi + 1, high);
        }
    }

    int partition(ArrayList<Dish> dishes, int low, int high) {
        String pivot = dishes.get(high).getName();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (dishes.get(j).getName().compareTo(pivot) < 0) {
                i++;
                Dish temp = dishes.get(i);
                dishes.set(i, dishes.get(j));
                dishes.set(j, temp);
            }
        }
        Dish temp = dishes.get(i + 1);
        dishes.set(i + 1, dishes.get(high));
        dishes.set(high, temp);
        return i + 1;
    }
}