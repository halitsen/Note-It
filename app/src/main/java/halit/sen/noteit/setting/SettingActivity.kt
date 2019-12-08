package halit.sen.noteit.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.changePassword.ChangePasswordActivity
import halit.sen.noteit.changePassword.ChangePasswordViewModel
import halit.sen.noteit.databinding.ActivitySettingBinding
import halit.sen.noteit.main.NoteViewModel
import halit.sen.noteit.main.NoteViewModelFactory
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.openInfoDialog

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingBinding
    private lateinit var notePreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting)
        viewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        binding.settingViewModel = viewModel
        binding.setLifecycleOwner(this)
        notePreference = SharedPreference(this)

        binding.settingBackIcon.setOnClickListener {
            finish()
        }
        binding.changePassLayout.setOnClickListener{

            if(notePreference.getPassword() != ""){
                viewModel.onChangePassLayoutClicked()
            }else{
                openInfoDialog(this,getString(R.string.set_password_yet), getString(R.string.password))
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
        binding.donateLayout.setOnClickListener {
            viewModel.onDonateClicked()
        }
    }
}
