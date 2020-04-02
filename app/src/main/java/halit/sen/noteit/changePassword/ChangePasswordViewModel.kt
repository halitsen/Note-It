package halit.sen.noteit.changePassword

import android.app.Application
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import halit.sen.noteit.R
import halit.sen.noteit.setting.SettingActivity
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.openInfoDialog

class ChangePasswordViewModel(app:Application, activity: ChangePasswordActivity) :AndroidViewModel(app){

    private val context = getApplication<Application>().applicationContext
    private lateinit var changePasswordActivity: ChangePasswordActivity

    private lateinit var notePreference: SharedPreference


    init {
        notePreference = SharedPreference(context)
        changePasswordActivity = activity
    }

    fun onChangePassButtonClicked(oldPass: String, newPass: String, newPassAgain: String){

        if(TextUtils.isEmpty(oldPass)){
            openInfoDialog(changePasswordActivity,context.getString(R.string.password_empty),context.getString(R.string.change_password))

        }else if(!notePreference.getPassword().equals(oldPass)){
            openInfoDialog(changePasswordActivity,context.getString(R.string.check_you_pass),context.getString(R.string.change_password))

        }else if(TextUtils.isEmpty(newPass) || TextUtils.isEmpty(newPassAgain)){
            openInfoDialog(changePasswordActivity,context.getString(R.string.password_empty),context.getString(R.string.change_password))
            return
        }else{
            notePreference.setPassword(newPass)
            val intent = Intent(context, SettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Toast.makeText(context,context.getString(R.string.password_changed),Toast.LENGTH_SHORT).show()
        }
    }
}