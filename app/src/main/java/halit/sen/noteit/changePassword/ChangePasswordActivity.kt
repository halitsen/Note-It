package halit.sen.noteit.changePassword

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.databinding.ActivityChangePasswordBinding
import halit.sen.noteit.databinding.ActivitySettingBinding
import halit.sen.noteit.setting.SettingViewModel

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var binding: ActivityChangePasswordBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password)
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel::class.java)
        binding.changePasswordViewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.changePassBackIcon.setOnClickListener {
            finish()
        }
        binding.changePassButton.setOnClickListener{

            viewModel.onChangePassButtonClicked()
        }
    }
}
