package halit.sen.noteit.changePassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.databinding.ActivityChangePasswordBinding
import halit.sen.noteit.utils.SharedPreference

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password)
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel::class.java)
        binding.changePasswordViewModel = viewModel
        binding.setLifecycleOwner(this)

        //todo eski şifreyi sharedpreferens daki şifre ile karşılaştıracam yeni şifreleri de birbiri ile karşılaştıracam.
        //todo tüm notları silmek istediğimde de şifre sorsun eğer şifre kurmuşsa, kumamışsa sormadan silsin.

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
