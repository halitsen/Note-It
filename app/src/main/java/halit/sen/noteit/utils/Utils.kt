package halit.sen.noteit.utils

import android.app.ProgressDialog.show
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import java.text.SimpleDateFormat
import java.util.*

fun getCurentTime(timeInMilis: Long): String {

    val date = Date(timeInMilis)
    val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}

fun getNoteTitleFromDescription(description: String): String {

    return ""
}

fun displayNoteInList(note: String): String {
    return when (note.length) {
        in 0..10 -> note
        in 11..20 -> note.substring(0, 10) + "..."
        else -> note.substring(0,15) + "..."
    }
}

fun openInfoDialog(context: Context, content: String, title: String) {
    val dialog = MaterialDialog.Builder(context)
        .title(title)
        .content(content)
        .positiveText("OK")
        .onPositive { dialog1, which -> dialog1.dismiss() }
        .show()
    dialog.titleView.textSize = 16f
    dialog.contentView!!.textSize = 14f
}