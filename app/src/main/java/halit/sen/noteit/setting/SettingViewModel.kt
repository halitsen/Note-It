package halit.sen.noteit.setting

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import halit.sen.noteit.changePassword.ChangePasswordActivity

class SettingViewModel(application: Application) : AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext

    fun onChangePassLayoutClicked(){
        val intent = Intent(context,ChangePasswordActivity::class.java)
        context.startActivity(intent)
    }
}