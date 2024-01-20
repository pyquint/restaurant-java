package src;

public class Item {
    String name;
    float price;
    String course;
    int amount;

    Item(String name, float price, String course) {
        this.name = name;
        this.price = price;
        this.course = course;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    int getAmount() {
        return this.amount;
    }
}
