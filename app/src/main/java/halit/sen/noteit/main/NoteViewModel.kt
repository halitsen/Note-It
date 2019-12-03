package halit.sen.noteit.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDao
import kotlinx.coroutines.*

class NoteViewModel(val database: NoteDao,
                    application: Application) : ViewModel(){
    //todo application kullanamazsam view model yerine android view model  extend edecem

    private var _allNotes: LiveData<List<Note>> = database.getAllNotes()
    val allNotes
    get() = _allNotes

    private var viewModelJob = Job()
    init {
        Log.i("NoteViewModel", "NoteViewModel created.")
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var note = MutableLiveData<Note>()

    override fun onCleared() {
        super.onCleared()
        Log.i("NoteViewModel", "NoteViewModel destroyed.")
        viewModelJob.cancel()
    }
}