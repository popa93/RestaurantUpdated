package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Drinks")
public class Drink implements Serializable, IMenuInterface {

    @ColumnInfo(name = "item_name")
    public String name;
    @ColumnInfo(name = "image_link")
    public String imageLink;
    @ColumnInfo(name = "price")
    public Long price;
    @ColumnInfo(name = "ingredients")
    public String ingredients;
    @ColumnInfo(name = "quantity")
    public int quantity;

    @PrimaryKey(autoGenerate = true)
    public int uuid;


    public Drink() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String imageLink() {
        return imageLink;

    }

    @Override
    public Long price() {
        return price;
    }

    @Override
    public int uuid() {
        return uuid;
    }

    @Override
    public String ingredients() {
        return ingredients;

    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
