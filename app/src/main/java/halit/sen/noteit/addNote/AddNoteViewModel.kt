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
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onAddNot(description: String) { //todo isLocked ve lastEditTime ı burada al title ı description un başından al
        //todo add mi update mi olduğuna dikkat et.. !!!
        uiScope.launch {
            val newNote = Note()
            newNote.noteTitle = getNoteTitleFromDescription(description)
            newNote.noteDescription = description
            newNote.noteIsLocked = _isNoteLocked.value!!
            newNote.noteLastEdit = java.util.Calendar.getInstance().timeInMillis
            withContext(Dispatchers.IO) {
                database.insert(newNote)
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