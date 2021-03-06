package model;

public
class Order {

    public String food;
    public String drink;
    public String total;
    public String date;
    public String orderStatus = "false";
    public String remarks;

    public Order() {
    }

    public Order(String food, String drink, String total, String date) {
        this.food = food;
        this.drink = drink;
        this.total = total;
        this.date = date;
    }

    public Order(String food, String drink, String total, String date, String orderStatus) {
        this.food = food;
        this.drink = drink;
        this.total = total;
        this.date = date;
        this.orderStatus = orderStatus;
    }

    public String getDrink() {
        return drink;
    }

    public String getFood() {
        return food;
    }

    public String getDate() {
        return date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
