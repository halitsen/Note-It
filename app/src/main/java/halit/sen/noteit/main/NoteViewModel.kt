package halit.sen.noteit.main

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import halit.sen.noteit.addNote.AddNoteActivity
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDao
import halit.sen.noteit.setting.SettingActivity
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.isNightModeActive
import kotlinx.coroutines.*

class NoteViewModel(val database: NoteDao,
                    application: Application) : AndroidViewModel(application){
    private val context = getApplication<Application>().applicationContext

    private var _allNotes: LiveData<List<Note>> = database.getAllNotes()
    val allNotes
    get() = _allNotes

    private var viewModelJob = Job()
    init {
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onProfileClicked(){
        val intent = Intent(context,SettingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun onAddNoteClicked(){
        val intent = Intent(context,AddNoteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}