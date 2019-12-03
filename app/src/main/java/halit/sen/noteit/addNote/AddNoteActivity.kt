package halit.sen.noteit.addNote

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDatabase
import halit.sen.noteit.databinding.ActivityAddNoteBinding
import halit.sen.noteit.main.NoteActivity
import halit.sen.noteit.main.NoteViewModel
import halit.sen.noteit.main.NoteViewModelFactory
import halit.sen.noteit.setting.SettingActivity
import java.nio.channels.InterruptedByTimeoutException

class AddNoteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel

    private lateinit var database: NoteDatabase
    var note :Note = Note()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        val application = requireNotNull(this).application
        val datasource = NoteDatabase.getInstance(application).noteDao
        //todo buraya gelen note varsa alıp edit et burada
        // editten geliyorsa intent ile note u set et uodate i çalıştır.
        val viewModelFactory = AddNoteViewModelFactory(note,datasource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddNoteViewModel::class.java)
        binding.addNoteViewModel = viewModel
        binding.setLifecycleOwner(this)
        database = NoteDatabase.getInstance(this)
        setSupportActionBar(binding.addNoteToolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.addNoteBackIcon.setOnClickListener {
            finish() // todo should I save the note before finish ?
        }
        binding.lockImage.setOnClickListener { image ->
           viewModel.lockNoteClicked()
        }
        viewModel.isNoteLocked.observe(this, Observer {
            if(it){
                binding.lockImage.setImageResource(R.drawable.ic_lock_close)
            }else{
                binding.lockImage.setImageResource(R.drawable.ic_lock_open)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save){
            if(binding.noteDescription.text.toString().length > 0){
                viewModel.onAddNot(binding.noteDescription.text.toString().trim())
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
