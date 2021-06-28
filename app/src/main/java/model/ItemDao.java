package model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public
interface ItemDao {

    @Query("SELECT * FROM Food")
    List<Pizza> getAllItems();

    @Query("SELECT * FROM Drinks")
    List<Drink> getAllDrinksItems();

    @Query("DELETE FROM Food")
    void deleteAllFood();

    @Query("DELETE FROM Drinks")
    void deleteAllDrinks();

    @Insert
    List<Long> insertAllDrinks(ArrayList<Drink> drinks);

    @Insert
    List<Long> insertAll(ArrayList<Pizza> pizzas); //return primary keys from list inserted
}
