package halit.sen.noteit.addNote

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDao
import halit.sen.noteit.getCurentTime
import halit.sen.noteit.getNoteTitleFromDescription
import kotlinx.coroutines.*

class AddNoteViewModel(val note: Note, val database: NoteDao, application: Application) :
    ViewModel() {

    // todo buraya gelen note null ise yeni note, null değil ise update note...
    // todo eklemek için geliniyorsa insert listeden nota tıklayıp geliniyorsa update olacak...

    private val _curentTime = MutableLiveData<String>()
    val curentTime: LiveData<String>
        get() = _curentTime

    private val _isNoteLocked = MutableLiveData<Boolean>()
    val isNoteLocked: LiveData<Boolean>
        get() = _isNoteLocked

    private val _editNoteDescription = MutableLiveData<String>()
    val editNoteDescription: LiveData<String>
    get() = _editNoteDescription

    val noteLockStatu = Transformations.map(isNoteLocked) {
        getLockStatu(it)
    }

    private fun getLockStatu(it: Boolean): Boolean {
        return it
    }

    init {
        Log.i("AddNoteViewModel", "AddNoteViewModel Created..")
        _isNoteLocked.value = false
        _curentTime.value = getCurentTime(java.util.Calendar.getInstance().timeInMillis)
        if(note.noteDescription.length>0){
            _editNoteDescription.value = note.noteDescription
            _isNoteLocked.value = note.noteIsLocked
        }
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

    fun onUpdateNote(description: String){
        uiScope.launch {
            note.noteDescription = description
            note.noteTitle = getNoteTitleFromDescription(description)
            note.noteLastEdit = java.util.Calendar.getInstance().timeInMillis
            note.noteIsLocked = _isNoteLocked.value!!
            withContext(Dispatchers.IO){
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