package halit.sen.noteit.setting

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import halit.sen.noteit.changePassword.ChangePasswordActivity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import halit.sen.noteit.R
import halit.sen.noteit.database.NoteDao
import halit.sen.noteit.main.NoteActivity
import halit.sen.noteit.utils.restart
import kotlinx.coroutines.*


class SettingViewModel(val database: NoteDao, application: Application) :
    AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {

    }

    fun onChangePassLayoutClicked() {
        val intent = Intent(context, ChangePasswordActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun onContactClicked() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = (context.getString(R.string.text_plain))
        intent.data = Uri.parse(context.getString(R.string.mailto))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.owner_mail)))
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val chooserIntent = Intent.createChooser(intent,context.getString(R.string.send_email))
            //context.startActivity(Intent.createChooser(intent, context.getString(R.string.send_email)))//
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooserIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, context.getString(R.string.no_mail_client), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun onShareClicked() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(
            Intent.EXTRA_TEXT,
            context.getString(R.string.share_app_url)
        )
        intent.type = (context.getString(R.string.text_plain))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val chooserIntent = Intent.createChooser(intent,context.getString(R.string.share))
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
        //context.startActivity(Intent.createChooser(intent,context.getString(R.string.share)))
    }

    fun onRateClicked() {
        val uri = Uri.parse(context.getString(R.string.playstore_url))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun deleteAllNotes() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.clear()
            }
        }
        Toast.makeText(context, context.getString(R.string.clear), Toast.LENGTH_SHORT).show()
        restart(context)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}