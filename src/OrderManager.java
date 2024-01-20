package src;

import java.util.HashMap;

public class OrderManager {
    MenuManager menuManager;
    HashMap<String, Item> orders;
    private String[] menuItems;
    private boolean isDiscountable;
    private float bill;
    private float payment;

    OrderManager(MenuManager menuManager, HashMap<String, Item> orders) {
        this.menuManager = menuManager;
        this.orders = orders;
    }

    String[] getOrderNames() {
        return this.orders.keySet().toArray(new String[0]);
    }

    void handleOrder() {
        menuItems = this.menuManager.getItemNames();
        isDiscountable = !Utils.getChoiceFromChoicesLoop("What type is the customer?", new String[]{"Regular", "PWD/Senior"}).equals("Regular");

        // Adding orders until customer is finished.
        do {
            addOrder();
        } while (Utils.getChoiceFromChoicesLoop("Is the customer finished ordering?",
                new String[]{"Continue Ordering", "Finish Order"}).equals("Continue Ordering"));

        // Confirming the order for more modifications
        String confirmation;
        while (!(confirmation = Utils.getChoiceFromChoicesLoop("Confirm order?",
                new String[]{"Yes, Confirm", "Add Order", "Modify Order", "Remove Order"})).equals("Yes, Confirm")) {
            switch (confirmation) {
                case "Add Order" -> addOrder();
                case "Modify Order" -> modifyOrderAmount();
                case "Remove Order" -> removeOrder();
            }
        }
        calculateBill();
        receivePayment();
        printReceipt();
        this.orders.clear();
    }

    void addOrder() {
        String itemOrderedName = Utils.getChoiceFromChoicesLoop("Which item would you like to order?", menuItems);
        Item itemOrdered = this.menuManager.menu.get(itemOrderedName);
        int amountOrdered = Utils.getIntInput(String.format("Amount of '%s': ", itemOrderedName), false);

        if (this.orders.containsKey(itemOrderedName)) {
            itemOrdered.setAmount(itemOrdered.getAmount() + amountOrdered);
            System.out.printf("MSG: Added %d more %s to orders.\n", amountOrdered, itemOrderedName);
        }
        else {
            itemOrdered.setAmount(amountOrdered);
            this.orders.put(itemOrderedName, itemOrdered);
        }
        System.out.println();
    }

    void modifyOrderAmount() {
        String itemToModify = Utils.getChoiceFromChoicesLoop("Which item would you like to change the amount of?", getOrderNames());
        int newAmount = Utils.getIntInput(String.format("New amount of '%s': ", itemToModify), false);
        this.orders.get(itemToModify).setAmount(newAmount);
        System.out.printf("MSG: Changed the amount of '%s' to '%d'.\n", itemToModify, newAmount);
    }

    void removeOrder() {
        String itemToRemove = Utils.getChoiceFromChoicesLoop("Which item would you like to remove?", getOrderNames());
        this.orders.remove(itemToRemove);
        System.out.printf("MSG: Removed '%s' from orders.\n", itemToRemove);
    }

    void printItems() {
        for (Item order: this.orders.values()) {
            System.out.printf("%s -> %d x $%f\n", order.name, order.amount, order.price);
        }
        System.out.println();
    }

    void receivePayment() {
        printItems();
        if (isDiscountable) {
            System.out.println("Discount: Applicable\n");
        }
        System.out.printf("Total: $%f\n", bill);
        while ((payment = Utils.getFloatInput("Payment: $", false)) < bill) {
            System.out.println("MSG: Insufficient payment.\n");
        }
        System.out.println();
    }

    void printReceipt() {
        System.out.printf("Total: $%f\n", bill);
        System.out.printf("Payment: $%f\n", payment);
        System.out.printf("Change: $%f\n\n", payment - bill);
        System.out.println("MSG: Thank you for dining!\n");
    }

    void calculateBill() {
        for (Item orderItem: this.menuManager.menu.values()) {
            bill += orderItem.amount * orderItem.price;
        }
        if (isDiscountable) {
            bill = (float) (bill - bill * 0.2);
        }
    }
}
