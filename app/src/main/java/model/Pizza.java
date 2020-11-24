package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Food")
public class Pizza implements Serializable {

    @ColumnInfo(name="item_name")
    public String name;
    @ColumnInfo(name="image_link")
    public String imageLink;
    @ColumnInfo(name="price")
    public Long price;
    @ColumnInfo(name="ingredients")
    public String ingredients;

    @PrimaryKey(autoGenerate = true)
    public int uuid;

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
