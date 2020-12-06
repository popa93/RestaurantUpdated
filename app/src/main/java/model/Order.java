package model;

public
class Order {

    public String email;
    public String food;
    public String drink;
    public String total;
    public String date;

    public Order(String food, String drink, String total, String date) {
        this.food = food;
        this.drink = drink;
        this.total = total;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public String getFood() {
        return food;
    }

    public String getDrink() {
        return drink;
    }

    public String getTotal() {
        return total;
    }

    public String getDate() {
        return date;
    }
}
