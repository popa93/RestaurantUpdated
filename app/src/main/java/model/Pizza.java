package model;

import java.io.Serializable;

public
class Pizza implements Serializable {

    private String name;
    private String imageLink;
    private Long price;
    private String ingredients;

    public Pizza(){

    }

    private Pizza(String name, String imageLink, Long price, String ingredients) {
        this.name = name;
        this.imageLink = imageLink;
        this.price=price;
        this.ingredients=ingredients;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
