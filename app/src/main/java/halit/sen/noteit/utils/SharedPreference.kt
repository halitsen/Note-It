package halit.sen.noteit.utils

import android.content.Context

class SharedPreference(context: Context) {

    val PREFERENCE_NAME = "noteItSharedPreference"
    val PASSWORD = "password"
    val MODE = "mode"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getPassword(): String? {
        return preference.getString(PASSWORD, "")
    }

    fun setPassword(password: String){
        val editor = preference.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun setMode(mode: String){
        val editor = preference.edit()
        editor.putString(MODE, mode)
        editor.apply()
    }

    fun getMode():String? {
        return preference.getString(MODE, "")
    }

}