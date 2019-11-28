package halit.sen.noteit.main

import android.util.Log
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel(){

    //todo application kullanamazsam view modl yerine android view model  extend edecem



    init {
        Log.i("NoteViewModel", "NoteViewModel created.")

    }


    override fun onCleared() {
        super.onCleared()
        Log.i("NoteViewModel", "NoteViewwModel destroyed.")

    }
}