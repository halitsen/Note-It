package halit.sen.noteit.changePassword

import android.app.Application
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import halit.sen.noteit.R

class ChangePasswordViewModel(app:Application) :AndroidViewModel(app){

    private val context = getApplication<Application>().applicationContext

    fun onChangePassButtonClicked(){
       Toast.makeText(context,"Password changed successfully",Toast.LENGTH_SHORT).show()
    }
}