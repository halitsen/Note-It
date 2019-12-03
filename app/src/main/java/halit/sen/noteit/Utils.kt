package halit.sen.noteit

import java.text.SimpleDateFormat
import java.util.*

fun getCurentTime(timeInMilis:Long): String{

    val date = Date(timeInMilis)
    val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}

fun getNoteTitleFromDescription(description:String):String{

    //todo title ı al
    return "this is title"

}

fun displayNoteInList(note:String):String{

    val noteSize = note.length

    if(noteSize >= 40){
        return note.substring(0, 30) + "..."
    }else{
        return note
    }
}