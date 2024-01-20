package src;

import java.util.HashMap;


public class Main {
    public static String[] courses = {"Appetizer", "src.Main", "Side", "Dessert", "Beverage"};
    public static MenuManager BigEggMenu = new MenuManager("Big Egg", courses, new HashMap<>());
    public static OrderManager BigEggOrders = new OrderManager(BigEggMenu, new HashMap<>());


    public static void main(String[] args) {
        while (true) {
            String user = Utils.getChoiceFromChoicesLoop("Are you the chef or crew?", new String[]{"Chef", "Crew", "Log Out"});

            if (user.equals("Chef")) {
                chefInterface();
            } else if (user.equals("Crew")) {
                if (!BigEggMenu.menu.isEmpty()) {
                    crewInterface();
                } else {
                    System.out.println("MSG: Sorry, the menu is currently empty.\n");
                }
            } else {
                System.out.println("MSG: Goodbye!");
                break;
            }
        }
    }

    public static void chefInterface() {
        System.out.println("MSG: Welcome, Chef!\n");

        loop: while (true) {
            String[] actions;
            if (BigEggMenu.menu.isEmpty()) {
                actions = new String[]{"Add src.Item", "Log Out as Chef"};
            }
            else {
                actions = new String[]{"Add src.Item", "Modify src.Item", "Remove src.Item", "Display Menu", "Log Out as Chef"};
            }
            String action = Utils.getChoiceFromChoicesLoop("What would you like to do?", actions);
            switch (action) {
                case "Add src.Item" -> BigEggMenu.addItem();
                case "Modify src.Item" -> BigEggMenu.modifyItem();
                case "Display Menu" -> BigEggMenu.displayMenu();
                case "Remove src.Item" -> BigEggMenu.removeItem();
                case "Log Out as Chef" -> {
                    break loop;
                }
            }
        }
    }

    public static void crewInterface() {
        System.out.println("MSG: Welcome, Crew!\n");

        loop: while (true) {
            String action = Utils.getChoiceFromChoicesLoop("What would you like to do?", new String[]{"Take Order", "Log Out as Crew"});

            switch (action) {
                case "Take Order" -> BigEggOrders.handleOrder();
                case "Log Out as Crew" -> {
                    break loop;
                }
            }
        }
    }
}