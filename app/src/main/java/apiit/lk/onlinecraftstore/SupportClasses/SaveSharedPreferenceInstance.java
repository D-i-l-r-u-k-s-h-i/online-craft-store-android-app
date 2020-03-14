package apiit.lk.onlinecraftstore.SupportClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferenceInstance {
    static final String SP_USER="username";
    static final String SP_ROLE="role";
    static final String SP_USER_ID="userId";
//    static final String SP_EMAIL="email";

    //pass the instance of context (Activity or fragment) which uses it
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    //set value, user-email at the creation of the sharedpreference
    public static void setUsername(Context ctx, String uname)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SP_USER, uname);
        editor.commit();
    }
    //retrieve values passed at the creation of shared preference
    public static String getUsername(Context ctx)
    {
        return getSharedPreferences(ctx).getString(SP_USER, "");
    }

    public static void setRole(Context ctx, String role)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SP_ROLE, role);
        editor.commit();
    }
    //retrieve values passed at the creation of shared preference
    public static String getRole(Context ctx)
    {
        return getSharedPreferences(ctx).getString(SP_ROLE, "");
    }

//    public static void setEmail(Context ctx, String email)
//    {
//        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
//        editor.putString(SP_EMAIL, email);
//        editor.commit();
//    }
//    //retrieve values passed at the creation of shared preference
//    public static String getEmail(Context ctx)
//    {
//        return getSharedPreferences(ctx).getString(SP_EMAIL, "");
//    }

    public static void setUserId(Context ctx, String uId)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SP_USER_ID, uId);
        editor.commit();
    }
    //retrieve values passed at the creation of shared preference
    public static String getUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(SP_USER_ID, "");
    }

    //clear shared preference for logout
    public static void clearUser(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
