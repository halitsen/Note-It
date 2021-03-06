package halit.sen.noteit.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.databinding.ActivityNoteBinding
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import halit.sen.noteit.R
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDatabase
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.isNightModeActive

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var notePreference: SharedPreference

    private lateinit var database: NoteDatabase
    var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        notePreference = SharedPreference(this)
        if(isNightModeActive(notePreference)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val application = requireNotNull(this).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NoteViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteViewModel::class.java)
        binding.noteViewModel = viewModel
        binding.setLifecycleOwner(this)
        database = NoteDatabase.getInstance(this)
        val adapter = NoteListAdapter()
        binding.noteList.adapter = adapter
        binding.noteToolbar
        setSupportActionBar(binding.noteToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.noteList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.allNotes.observe(this, Observer {
            it?.let {
                adapter.data = it
                binding.emptyNotePaceholderText.visibility = View.GONE
            }
            if(it.size < 1){
                binding.emptyNotePaceholderText.visibility = View.VISIBLE
            }
        })

        binding.fab.setOnClickListener {
            viewModel.onAddNoteClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.profile){
            viewModel.onProfileClicked()
        }
        return super.onOptionsItemSelected(item)
    }
}
