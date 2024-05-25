import java.util.*;

public class Client extends User implements OrderManagement {
    private final ArrayList<Dish> cart;

    public Client(String name) {
        super(name);
        this.cart = new ArrayList<>();
    }
    void viewItems() {
        if (cart.isEmpty()) {
            System.out.println("The cart is empty, there is nothing to view.");
            return;
        }
        System.out.println("------------ Your Cart ------------");

        for (Dish dish : cart) {
            System.out.println("- " + dish.getName() + ", $" + String.format("%.2f", dish.getPrice()));
        }
        System.out.println("------------------------------------");
        System.out.println("Total: $" +  String.format("%.2f", calculateTotalPrice()));
    }

    @Override
    public void addToOrder(RestaurantManager restaurantManager) {
        try {
            if (restaurantManager.menus.isEmpty()) {
                System.out.println("No menus available.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            int menuCount = 1;
            for (Menu menu : restaurantManager.menus) {
                System.out.println(menuCount++ + " - " + menu.restaurantName + " ");
            }
            System.out.println("Which menu would you like to order from?");
            int menuSelection = scanner.nextInt();
            scanner.nextLine();
            if (menuSelection > 0 && menuSelection <= restaurantManager.menus.size()) {
                Menu menuChoice = restaurantManager.menus.get(menuSelection - 1);
                if (menuChoice.dishes.isEmpty()) {
                    System.out.println("No dishes available.");
                    return;
                }
                while (true) {
                    int dishCount = 1;
                    for (Dish dish : menuChoice.dishes) {
                        System.out.println(dishCount++ + " - " + dish.getName() + " $" + String.format("%.2f", dish.getPrice()) + " ");
                    }
                    System.out.println("Which dish would you like to order?");
                    int dishSelection = scanner.nextInt();
                    scanner.nextLine();
                    if (dishSelection > 0 && dishSelection <= menuChoice.dishes.size()) {
                        Dish dishChoice = menuChoice.dishes.get(dishSelection - 1);
                        cart.add(dishChoice);
                        System.out.println(dishChoice.getName() + " added successfully to you cart!");
                    } else {
                        System.out.println("Invalid input. ");
                        break;
                    }
                    System.out.println("Do you want to add another dish from the same menu? (Type 'y' to continue or any other key to go back)");
                    String choice = scanner.nextLine();
                    if (!choice.equalsIgnoreCase("y")) {
                        break;
                    }
                }
            } else {
                System.out.println("Invalid input. ");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. No dish added.");
            addToOrder(restaurantManager);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. ");
            System.out.println(e.getMessage());
            addToOrder(restaurantManager);
        }
    }

    public void removeFromOrder() {
        try {
            Scanner scanner = new Scanner(System.in);
            if (cart.isEmpty()) {
                System.out.println("The cart is empty, there's nothing to remove.");
                return;
            }


            for (int i = 0; i < cart.size(); i++) {
                System.out.println((i + 1) + " - " + cart.get(i).getName());
            }
            System.out.println("Which item would you like to remove from your cart?");
            int selection = scanner.nextInt();
            scanner.nextLine();
            if (selection > 0 && selection <= cart.size()) {
                Dish removedItem = cart.remove(selection - 1);
                System.out.println(removedItem.getName() + " has been removed from the cart.");
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid option.");
            removeFromOrder();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            System.out.println(e.getMessage());
            removeFromOrder();
        }
    }


    void pay() {
        if (cart.isEmpty()) {
            System.out.println("The cart is empty, there is nothing to pay for.");
            return;
        }

        double totalPrice = calculateTotalPrice();
        System.out.println("Total price to pay: $" + String.format("%.2f", totalPrice));
        System.out.println("Payment successful. Thank you for your order!");
        cart.clear();
    }

    @Override
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Dish dish : cart) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }
}

