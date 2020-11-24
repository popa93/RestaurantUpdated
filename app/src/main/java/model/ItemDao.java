package model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public
interface ItemDao {

    @Insert
    List<Long> insertAll(Pizza... pizzas); //return primary keys from dog list inserted

    @Query("SELECT * FROM Food")
    List<Pizza> getAllItems();

    @Query("DELETE FROM Food")
    void deleteAllFood();
}
