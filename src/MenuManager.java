package src;

import java.util.HashMap;

public class MenuManager {
    String establishment;
    String[] courses;
    HashMap<String, Item> menu;

    MenuManager(String establishment, String[] courses, HashMap<String, Item> menu) {
        this.establishment = establishment;
        this.courses = courses;
        this.menu = menu;
    }

    String[] getItemNames() {
        return this.menu.keySet().toArray(new String[0]);
    }

    void addItem() {
        String newItemName = Utils.getStringInput("Enter name of the new item: ");
        float newItemPrice = Utils.getFloatInput(String.format("Enter the price of '%s': $", newItemName), false);
        String newItemCourse = Utils.getChoiceFromChoicesLoop(String.format("\nWhich course is '%s'?", newItemName), this.courses);

        this.menu.put(newItemName, new Item(newItemName, newItemPrice, newItemCourse));
        System.out.printf("MSG: Successfully added '%s' to the menu.\n\n", newItemName);
    }

    void removeItem() {
        this.menu.remove(Utils.getChoiceFromChoicesLoop("Enter name of item to remove: ", getItemNames()));
    }

    void modifyItem()  {
        while (true) {
            String itemToChangeName = Utils.getChoiceFromChoicesLoop("Which item would you change?", getItemNames());
            Item itemToChange = this.menu.get(itemToChangeName);
            String attrToChange = Utils.getChoiceFromChoicesLoop(String.format("What do you want to change with '%s'?", itemToChangeName), new String[]{"Name", "Price"});

            if (attrToChange.equals("Name")) {
                String newName = Utils.getStringInput(String.format("Enter new name of '%s': ", itemToChangeName));
                itemToChange.name = newName;
                this.menu.put(newName, itemToChange);
                this.menu.remove(itemToChangeName);
                break;
                }
            else if (attrToChange.equals("Price")) {
                this.menu.get(itemToChangeName).price = Utils.getFloatInput("Enter new price of item: $", false);
                break;
                }
            }
        }

    void displayMenu() {
        // TODO Categorize menu based on src.Item.course...
        System.out.println("MSG: Displaying menu...");
        this.menu.forEach(
                (itemName, item)
                        -> System.out.printf("[%s] %s -> $%f\n", item.course, itemName, item.price)
        );
        System.out.println();
    }
}