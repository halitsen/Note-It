package halit.sen.noteit.addNote

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.database.Note
import halit.sen.noteit.database.NoteDatabase
import halit.sen.noteit.databinding.ActivityAddNoteBinding
import halit.sen.noteit.databinding.LockInfoDialogBinding
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.openInfoDialog

class AddNoteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var dialogBinding: LockInfoDialogBinding
    private lateinit var viewModel: AddNoteViewModel
    private lateinit var database: NoteDatabase
    private lateinit var notePreference: SharedPreference
    var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        val application = requireNotNull(this).application
        val datasource = NoteDatabase.getInstance(application).noteDao
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            note = bundle.getSerializable("note") as Note
        }
        val viewModelFactory = AddNoteViewModelFactory(note, datasource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddNoteViewModel::class.java)
        binding.addNoteViewModel = viewModel
        binding.setLifecycleOwner(this)
        database = NoteDatabase.getInstance(this)
        setSupportActionBar(binding.addNoteToolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        notePreference = SharedPreference(this)
        binding.addNoteBackIcon.setOnClickListener {
            finish()
        }
        binding.lockImage.setOnClickListener { image ->

            if (notePreference.getPassword() != "") {
                viewModel.lockNoteClicked()
            } else {
                createLockInfoDialog()
            }
        }
        viewModel.isNoteLocked.observe(this, Observer {
            if (it) {
                binding.lockImage.setImageResource(R.drawable.ic_lock_close)
            } else {
                binding.lockImage.setImageResource(R.drawable.ic_lock_open)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save -> {
                if (binding.noteDescription.text.toString().length > 0) {
                    if (note.noteDescription.length == 0) {
                        viewModel.onAddNote(binding.noteDescription.text.toString().trim())
                    } else {
                        viewModel.onUpdateNote(binding.noteDescription.text.toString().trim())
                    }
                }
                finish()
            }
            R.id.share ->{viewModel.shareNote(binding.noteDescription.text.trim().toString())}
            R.id.delete -> {viewModel.deleteNote()}

        }


        if (item.itemId == R.id.save) {
            if (binding.noteDescription.text.toString().length > 0) {
                if (note.noteDescription.length == 0) {
                    viewModel.onAddNote(binding.noteDescription.text.toString().trim())
                } else {
                    viewModel.onUpdateNote(binding.noteDescription.text.toString().trim())
                }
            }

            finish()
        }


        return super.onOptionsItemSelected(item)
    }

    fun createLockInfoDialog() {
        dialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.lock_info_dialog,
            null,
            false
        )
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.createPasswordText.setOnClickListener {
            val password = dialogBinding.passEditText.text.trim().toString()
            val passwordAgain = dialogBinding.passAgainEditText.text.trim().toString()
            if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain)) {
                openInfoDialog(
                    this,
                    getString(R.string.pass_empty_warning),
                    getString(R.string.password)
                )
                return@setOnClickListener
            } else if (!password.equals(passwordAgain)) {
                openInfoDialog(
                    this,
                    getString(R.string.pass_match_warning),
                    getString(R.string.password)
                )
                return@setOnClickListener
            } else {
                notePreference.setPassword(password)
                Toast.makeText(this, getString(R.string.password_created), Toast.LENGTH_SHORT)
                    .show()
                viewModel.lockNoteClicked()
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}
