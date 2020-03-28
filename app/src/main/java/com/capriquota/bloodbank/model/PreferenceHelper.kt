package com.capriquota.bloodbank.model

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val INTRO = "intro"
    private val NAME = "name"
    private val USERNAME = "username"
    private val PASSWORD = "password"
    private val app_prefs:  SharedPreferences = context.getSharedPreferences(
        "shared",
        Context.MODE_PRIVATE
    )

    fun putIsLogin(loginorout: Boolean) {
        val edit = app_prefs.edit()
        edit.putBoolean(INTRO, loginorout)
        edit.apply()
    }

    fun getIsLogin(): Boolean {
        return app_prefs.getBoolean(INTRO, false)
    }


    fun putName(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(NAME, loginorout)
        edit.apply()
    }

    fun getName(): String? {
        return app_prefs.getString(NAME, "")
    }


    fun putUserName(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(USERNAME, loginorout)
        edit.apply()
    }

    fun getUserName(): String? {
        return app_prefs.getString(USERNAME, "")
    }

    fun putPassword(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(PASSWORD, loginorout)
        edit.apply()
    }

    fun getPassword(): String? {
        return app_prefs.getString(PASSWORD, "")
    }


}