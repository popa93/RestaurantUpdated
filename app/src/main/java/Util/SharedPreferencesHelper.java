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


}
