package model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pizza.class},version =1)
public abstract class ItemsDatabase extends RoomDatabase {

    private static ItemsDatabase databaseInstance=null;

    public static ItemsDatabase getInstance(Context context){

            if (databaseInstance==null){

                databaseInstance= Room.databaseBuilder(context.getApplicationContext(),ItemsDatabase.class,"itemsDatabase").build();
            }

            return databaseInstance;
    }

    public abstract ItemDao itemDao();
}
