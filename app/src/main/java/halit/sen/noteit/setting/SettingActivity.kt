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

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting)
        viewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        binding.settingViewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.settingBackIcon.setOnClickListener {
            finish()
        }
        binding.changePassLayout.setOnClickListener{
            viewModel.onChangePassLayoutClicked()
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
