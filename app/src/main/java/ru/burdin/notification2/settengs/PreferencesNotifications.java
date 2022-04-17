package ru.burdin.notification2.settengs;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import ru.burdin.notification2.MainActivity;

import static java.security.AccessController.getContext;

public   final  class PreferencesNotifications  {

    private  static PreferencesNotifications preferencesNotifications;
    public  static  final  String APP_PREFERENCES_NOTIFICATION ="notification";
    public  static  final  String APP_PREFERENCES_ISCHECKED ="isChecked";
    private SharedPreferences preferences;

    private  PreferencesNotifications(Context context) {
preferences = context.getSharedPreferences(APP_PREFERENCES_NOTIFICATION,0);
    }

    public  static  PreferencesNotifications getPreferencesNotifications(Context context) {
        if (preferencesNotifications == null) {
            preferencesNotifications = new PreferencesNotifications(context);
        }
    return preferencesNotifications;
    }

public  void save(String key, boolean value) {
    SharedPreferences.Editor editor = preferences.edit();
editor.putBoolean(key, value);
    editor.apply();
    }

public  void save ( String key, float value) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putFloat(key, value);
    editor.apply();
    }

    public  void  save (String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public  String getString (String key) {
return  preferences.getString(key, null);
    }

    public  boolean getBooleanValue(String key) {
        boolean res = false;
        if (preferences.contains(key)){
            res = preferences.getBoolean(key, true);
        }
return  res;
    }

    public  float getFloatValue(String key) {
        float res = 1.0f;
        if (preferences.contains(key)){
            res = preferences.getFloat(key, 1.0f);
        }
        return  res;
    }

}
