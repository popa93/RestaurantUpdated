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
        // prefs.edit().clear().commit();

    }


    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    public void saveUpdateTime(long time) {

        prefs.edit().putLong(Constants.PREF_TIME, time).apply();
    }


    public long getUpdateTime() {
        return prefs.getLong(Constants.PREF_TIME, 0);
    }

    public void setLastBackendDownloadDate(int day) {
        prefs.edit().putInt(Constants.PREF_DATE, day).apply();

    }

    public int getLastBackendDownloadDate() {

        return prefs.getInt(Constants.PREF_DATE, 0);
    }

    public void setTheme(boolean client, boolean kitchen, boolean admin) {

        if (client)
            prefs.edit().putString(Constants.CLIENT_THEME, Constants.CLIENT_THEME).apply();
        else if (kitchen)
            prefs.edit().putString(Constants.KITCHEN_THEME, Constants.KITCHEN_THEME).apply();
        else if (admin) {
            prefs.edit().putString(Constants.ADMIN_THEME, Constants.ADMIN_THEME).apply();
        }


    }
    public void firstRun(boolean firstRun) {
        prefs.edit().putBoolean("run", true).apply();
    }

    public boolean getFirstRun() {
        return prefs.getBoolean("run", false);
    }

    public String getTheme() {
        if (!prefs.getString(Constants.CLIENT_THEME, "").equals("")) {
            return Constants.CLIENT_THEME;
        } else if (!prefs.getString(Constants.KITCHEN_THEME, "").equals("")) {
            return Constants.KITCHEN_THEME;
        } else if (!prefs.getString(Constants.ADMIN_THEME, "").equals("")) {
            return Constants.ADMIN_THEME;
        } else {
            return Constants.NO_THEME;
        }
    }

    public void setChildCallTrue() {

        prefs.edit().putBoolean("first", true).apply();
    }

    public void setChildCallFalse() {

        prefs.edit().putBoolean("first", false).apply();
    }

    public boolean getChildCall() {
        return prefs.getBoolean("first", false);
    }

    public void setCurrentDeviceEmui(String emui) {
        prefs.edit().putString("currentEmui", emui).apply();
    }

    public String getCurrentDeviceEmui() {
        return prefs.getString("currentEmui", "error");
    }

}
