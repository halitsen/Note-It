package halit.sen.noteit.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import halit.sen.noteit.R
import halit.sen.noteit.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting)
        viewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        binding.settingViewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.addSettingBackIcon.setOnClickListener {
            finish()
        }
    }
}
