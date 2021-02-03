package com.mufiid.visitblitar.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mufiid.visitblitar.BuildConfig
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.databinding.ActivitySplashScreenBinding
import com.mufiid.visitblitar.ui.login.LoginActivity
import com.mufiid.visitblitar.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.tvAppVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}