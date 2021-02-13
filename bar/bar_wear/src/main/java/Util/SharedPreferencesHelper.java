package Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public
class SharedPreferencesHelper {
    private static SharedPreferencesHelper instance;
    private SharedPreferences prefs;

    private SharedPreferencesHelper(Context context) {

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //prefs.edit().clear().commit();

    }


    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    public void setCurrentDate(String currentDate) {
        prefs.edit().putString("date", currentDate).apply();
    }

    public String getCurrentDate() {
        return prefs.getString("date", "");
    }

    public void setStatusModifCounter(int value) {
        prefs.edit().putInt("counter", value).apply();
    }

    public int getStatusModifCounter() {
        return prefs.getInt("counter", 0);
    }

    public void setGlobalStatusCoutner(int value) {
        prefs.edit().putInt("global", value).apply();
    }

    public int getGlobalStatusCounter() {
        return prefs.getInt("global", 0);

    }

}
