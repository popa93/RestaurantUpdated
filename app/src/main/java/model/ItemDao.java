package model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public
interface ItemDao {

    @Insert
    List<Long> insertAll(ArrayList<Pizza> pizzas); //return primary keys from dog list inserted

    @Insert
    List<Long> insertAllDrinks(ArrayList<Drink> drinks);

    @Query("SELECT * FROM Food")
    List<Pizza> getAllItems();

    @Query("SELECT * FROM Drinks")
    List<Drink> getAllDrinksItems();

    @Query("DELETE FROM Food")
    void deleteAllFood();

    @Query("DELETE FROM Drinks")
    void deleteAllDrinks();
}
