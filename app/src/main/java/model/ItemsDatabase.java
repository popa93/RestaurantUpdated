package model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import Util.Constants;

@Database(entities = {Pizza.class, Drink.class}, version = 4)
public abstract class ItemsDatabase extends RoomDatabase {

    private static ItemsDatabase databaseInstance = null;

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(Constants.CREATE_DRINKS_TABLE);
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(Constants.FOOD_TABLE_ADD_QUANTITY_COLUMN);
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(Constants.DRINKS_TABLE_ADD_QUANTITY_COLUMN);
        }
    };

    public static ItemsDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext()
                    , ItemsDatabase.class, Constants.DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build();
        }
        return databaseInstance;
    }

    public abstract ItemDao itemDao();
}
