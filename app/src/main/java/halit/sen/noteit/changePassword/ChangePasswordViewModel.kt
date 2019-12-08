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

class ChangePasswordViewModel(app:Application) :AndroidViewModel(app){

    private val context = getApplication<Application>().applicationContext

    private lateinit var notePreference: SharedPreference

    init {
        notePreference = SharedPreference(context)
    }

    fun onChangePassButtonClicked(oldPass: String, newPass: String, newPassAgain: String){

        if(TextUtils.isEmpty(oldPass)){
            openInfoDialog(context,"Password is empty","Change Password")
            return
        }else if(!notePreference.getPassword().equals(oldPass)){
            openInfoDialog(context,"Check your password","Change Password")
            return
        }else if(TextUtils.isEmpty(newPass) || TextUtils.isEmpty(newPassAgain)){
            openInfoDialog(context,"Password is empty","Change Password")
            return
        }else{
            notePreference.setPassword(newPass)
            val intent = Intent(context, SettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
            Toast.makeText(context,"Password Changed..",Toast.LENGTH_SHORT).show()
        }
    }
}