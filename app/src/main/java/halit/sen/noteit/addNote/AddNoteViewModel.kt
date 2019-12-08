package halit.sen.noteit.addNote

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDao
import halit.sen.noteit.utils.getCurentTime
import halit.sen.noteit.utils.getNoteTitleFromDescription
import kotlinx.coroutines.*
import java.util.*

class AddNoteViewModel(val note: Note, val database: NoteDao, application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _curentTime = MutableLiveData<String>()
    val curentTime: LiveData<String>
        get() = _curentTime

    private val _isPasswordCreated = MutableLiveData<Boolean>()
    val isPasswordCreated: LiveData<Boolean>
    get() = _isPasswordCreated

    private val _isNoteLocked = MutableLiveData<Boolean>()
    val isNoteLocked: LiveData<Boolean>
        get() = _isNoteLocked

    private val _editNoteDescription = MutableLiveData<String>()
    val editNoteDescription: LiveData<String>
        get() = _editNoteDescription

    init {
        Log.i("AddNoteViewModel", "AddNoteViewModel Created..")
        _curentTime.value =
            getCurentTime(Calendar.getInstance().timeInMillis)
        _editNoteDescription.value = note.noteDescription
        _isNoteLocked.value = note.noteIsLocked
        _isPasswordCreated.value = false
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onAddNote(description: String) {
        uiScope.launch {
            note.noteTitle = getNoteTitleFromDescription(description)
            note.noteDescription = description
            note.noteIsLocked = _isNoteLocked.value!!
            note.noteLastEdit = java.util.Calendar.getInstance().timeInMillis
            withContext(Dispatchers.IO) {
                database.insert(note)
            }
        }
    }

    fun onUpdateNote(description: String) {
        uiScope.launch {
            note.noteDescription = description
            note.noteTitle = getNoteTitleFromDescription(description)
            note.noteLastEdit = java.util.Calendar.getInstance().timeInMillis
            note.noteIsLocked = _isNoteLocked.value!!
            withContext(Dispatchers.IO) {
                database.update(note)
            }
        }
    }

    fun lockNoteClicked() {
        _isNoteLocked.value = _isNoteLocked.value == false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("AddNoteViewModel", "AddNoteViewModel Destroyed..")
        viewModelJob.cancel()
    }
}