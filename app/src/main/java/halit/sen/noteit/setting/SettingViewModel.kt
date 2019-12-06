package halit.sen.noteit.setting

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import halit.sen.noteit.changePassword.ChangePasswordActivity
import android.net.Uri
import android.widget.Toast
import halit.sen.noteit.R


class SettingViewModel(application: Application) : AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext

    fun onChangePassLayoutClicked(){
        val intent = Intent(context,ChangePasswordActivity::class.java)
        context.startActivity(intent)
    }

    fun onContactClicked(){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = (context.getString(R.string.text_plain))
        intent.data = Uri.parse(context.getString(R.string.mailto))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.owner_mail)))
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        try {
            context.startActivity(Intent.createChooser(intent,context.getString(R.string.send_email)))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context,context.getString(R.string.no_mail_client),Toast.LENGTH_SHORT).show()
        }
    }

    fun onShareClicked(){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,
            context.getString(R.string.share_app_url))
        intent.type=(context.getString(R.string.text_plain))
        context.startActivity(intent)
    }

    fun onRateClicked(){
        val uri = Uri.parse(context.getString(R.string.playstore_url))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    fun onDonateClicked(){
        //reklam ekleyene kadar ödemeyi ekleme..
        // todo pay 2$ to remove the add...
    }
}