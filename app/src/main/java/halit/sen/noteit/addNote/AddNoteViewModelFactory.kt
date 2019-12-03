package halit.sen.noteit.addNote

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDao
import halit.sen.noteit.main.NoteViewModel

class AddNoteViewModelFactory (private val note: Note,
                               private val dataSource: NoteDao,
                               private val application: Application): ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            return AddNoteViewModel(note,dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}