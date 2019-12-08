package halit.sen.noteit.changePassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var binding: ActivityChangePasswordBinding

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
