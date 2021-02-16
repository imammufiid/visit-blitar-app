package com.mufiid.visitblitar.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.databinding.ActivityLoginBinding
import com.mufiid.visitblitar.ui.main.MainActivity
import com.mufiid.visitblitar.ui.registration.RegistrationActivity
import com.mufiid.visitblitar.utils.pref.AuthPref
import com.mufiid.visitblitar.utils.pref.UserPref

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    companion object {
        const val CHECK_LOGIN = "check_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        checkLogin()
    }

    private fun checkLogin() {
        if (AuthPref.isLoggedIn(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun init() {

        // init view model
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.message.observe(this, {
            if (it.isNotEmpty()) {
                showMessage(it)
            }
        })

        viewModel.user.observe(this, {
            if (it != null) {
                showMessage("Berhasil Login")
                UserPref.setUserData(this, it)
                AuthPref.setIsLoggedIn(this, true)

                val checkLogin = intent.getBooleanExtra(CHECK_LOGIN, false)
                if (checkLogin) {
                    finish()
                } else {
                    Handler(mainLooper).postDelayed({
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }, 1000)
                }
            }
        })

        viewModel.loading.observe(this, {
            if (it) {
                binding.layoutButton.visibility = View.GONE
                binding.progressBarLogin.visibility = View.VISIBLE
            } else {
                binding.layoutButton.visibility = View.VISIBLE
                binding.progressBarLogin.visibility = View.GONE
            }
        })

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegistration.setOnClickListener(this)
    }

    private fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                if (username == "" || password == "") {
                    showToast("Field harus diisi")
                } else {
                    viewModel.loginUser(username, password)
                }
            }
            R.id.btn_registration -> {
                startActivity(Intent(this, RegistrationActivity::class.java))
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}