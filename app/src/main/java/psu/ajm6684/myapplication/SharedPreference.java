package psu.ajm6684.myapplication;

import android.content.Context;

public class SharedPreference {

    android.content.SharedPreferences pref;
    android.content.SharedPreferences.Editor editor;
    Context _context;
    private static final String PREF_NAME = "testing";
    
    public static final String KEY_SET_APP_RUN_FIRST_TIME = "KEY_SET_APP_RUN_FIRST_TIME";


    public SharedPreference(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();

    }



    public void setApp_runFirst(String App_runFirst)
    {
        editor.remove(KEY_SET_APP_RUN_FIRST_TIME);
        editor.putString(KEY_SET_APP_RUN_FIRST_TIME, App_runFirst);
        editor.apply();
    }

    public String getApp_runFirst()
    {
        String  App_runFirst= pref.getString(KEY_SET_APP_RUN_FIRST_TIME, "FIRST");
        return  App_runFirst;
    }

}