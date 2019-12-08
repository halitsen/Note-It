package halit.sen.noteit.utils

import android.content.Context

class SharedPreference(context: Context) {

    val PREFERENCE_NAME = "noteItSharedPreference"
    val PASSWORD = "password"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getPassword(): String? {
        return preference.getString(PASSWORD, "")
    }

    fun setPassword(password: String){
        val editor = preference.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }

}