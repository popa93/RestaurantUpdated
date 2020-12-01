package model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Pizza.class, Drink.class}, version = 2)
public abstract class ItemsDatabase extends RoomDatabase {

    private static ItemsDatabase databaseInstance = null;

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'Drinks' ( 'uuid' integer primary key autoincrement not null ,'image_link' TEXT,'item_name' TEXT,'price' integer,'ingredients' TEXT)");
        }
    };


    public static ItemsDatabase getInstance(Context context) {

        if (databaseInstance == null) {

            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), ItemsDatabase.class, "itemsDatabase").addMigrations(MIGRATION_1_2).build();
        }

        return databaseInstance;
    }

    public abstract ItemDao itemDao();
}
