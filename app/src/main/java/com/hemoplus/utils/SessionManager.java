package com.hemoplus.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hemoplus.models.User;

public class SessionManager {
    private static final String PREF_NAME = "HemoPlusSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_BLOOD_TYPE = "bloodType";
    private static final String KEY_USER_TYPE = "userType";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveUserSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FULL_NAME, user.getFullName());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_BLOOD_TYPE, user.getBloodType());
        editor.putString(KEY_USER_TYPE, user.getUserType());
        editor.commit();
    }

    public User getUserSession() {
        if (!isLoggedIn()) {
            return null;
        }

        User user = new User();
        user.setId(pref.getInt(KEY_USER_ID, 0));
        user.setEmail(pref.getString(KEY_EMAIL, ""));
        user.setFullName(pref.getString(KEY_FULL_NAME, ""));
        user.setPhone(pref.getString(KEY_PHONE, ""));
        user.setBloodType(pref.getString(KEY_BLOOD_TYPE, ""));
        user.setUserType(pref.getString(KEY_USER_TYPE, ""));
        return user;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}