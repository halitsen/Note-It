package halit.sen.noteit.setting

import android.app.Dialog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.database.NoteDatabase
import halit.sen.noteit.databinding.ActivitySettingBinding
import halit.sen.noteit.main.NoteActivity
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.isNightModeActive
import halit.sen.noteit.utils.openInfoDialog
import halit.sen.noteit.utils.restart

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingBinding
    private lateinit var notePreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)


        notePreference = SharedPreference(this)

        if(isNightModeActive(notePreference)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.nightModeSwitch.isChecked = true
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.nightModeSwitch.isChecked = false
        }

        val application = requireNotNull(this).application
        val datasource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = SettingViewModelFactory(datasource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java)
        binding.settingViewModel = viewModel
        binding.setLifecycleOwner(this)
        notePreference = SharedPreference(this)

        binding.settingBackIcon.setOnClickListener {
            startActivity(Intent(this,NoteActivity::class.java))
            finish()
        }
        binding.changePassLayout.setOnClickListener {

            if (notePreference.getPassword() != "") {
                viewModel.onChangePassLayoutClicked()
            } else {
                openInfoDialog(
                    this,
                    getString(R.string.set_password_yet),
                    getString(R.string.password)
                )
            }
        }
        binding.contactLayout.setOnClickListener {
            viewModel.onContactClicked()
        }
        binding.rateLayout.setOnClickListener {
            viewModel.onRateClicked()
        }
        binding.shareLayout.setOnClickListener {
            viewModel.onShareClicked()
        }
        binding.deleteLayout.setOnClickListener {
            if (notePreference.getPassword() != ""){
                getPasswordDialog()
            }else{
                getDecisionDialog()
            }
        }

        binding.nightModeSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->

            if(isChecked){
                Toast.makeText(this,getString(R.string.night_mode_active),Toast.LENGTH_SHORT).show()
                notePreference.setMode("night")
                Handler().postDelayed({
                    restart(this)
                }, 200)
            }else{
                Toast.makeText(this,getString(R.string.day_mode_active),Toast.LENGTH_SHORT).show()
                notePreference.setMode("day")
                Handler().postDelayed({
                    restart(this)
                }, 200)
            }
        }
    }

    fun getPasswordDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.password_dialog)
        val notePreference = SharedPreference(this)
        val pass: EditText = dialog.findViewById(R.id.password_edit_text)
        val cancel: TextView = dialog.findViewById(R.id.cancel_text)
        val okey: TextView = dialog.findViewById(R.id.okey__text)

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okey.setOnClickListener {
            val password = pass.text.trim().toString()
            if (password.equals(notePreference.getPassword())) {
                viewModel.deleteAllNotes()
                val intent = Intent(this, NoteActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                dialog.dismiss()
            } else {
                openInfoDialog(
                    this,
                    getString(R.string.wron_pass_warning),
                    getString(R.string.password)
                )
                return@setOnClickListener
            }
        }
        dialog.show()
    }

    fun getDecisionDialog(){

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.delete_notes_dialog)
        val cancel: TextView = dialog.findViewById(R.id.cancel)
        val okey: TextView = dialog.findViewById(R.id.okey)

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okey.setOnClickListener {
            viewModel.deleteAllNotes()
        }
        dialog.show()
    }
}
