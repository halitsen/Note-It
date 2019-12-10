package halit.sen.noteit.changePassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.databinding.ActivityChangePasswordBinding
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.isNightModeActive

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password)
        var notePreference: SharedPreference

        notePreference = SharedPreference(this)

        if(isNightModeActive(notePreference)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel::class.java)
        binding.changePasswordViewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.changePassBackIcon.setOnClickListener {
            finish()
        }
        binding.changePassButton.setOnClickListener{
            viewModel.onChangePassButtonClicked(binding.oldPassEditText.text.trim().toString(),
                binding.newPassEditText.text.toString(),
                binding.newPassAgainEditText.text.trim().toString())
        }
    }
}
