package com.mufiid.visitblitar.ui.registration

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.UserEntity
import com.mufiid.visitblitar.databinding.ActivityRegistrationBinding
import com.mufiid.visitblitar.ui.login.LoginActivity
import com.mufiid.visitblitar.view.RegistrationView
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RegistrationActivity : AppCompatActivity(), RegistrationView, View.OnClickListener {
    private lateinit var binding: ActivityRegistrationBinding
    private var presenter: RegistrationPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
    }

    private fun init() {
        CompositeDisposable().clear()
        presenter = RegistrationPresenter(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun successRegistration(message: String?, user: UserEntity) {
        showToast(message)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1000)
    }

    override fun failedRegistration(message: String?) {
        showToast(message)
    }

    override fun loading(state: Boolean) {
        if (state) {
            binding.layoutButton.visibility = View.GONE
            binding.progressBarRegister.visibility = View.VISIBLE
        } else {
            binding.layoutButton.visibility = View.VISIBLE
            binding.progressBarRegister.visibility = View.GONE
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                val username = binding.etUsername.text.toString()
                val firstName = binding.etFirstname.text.toString()
                val lastName = binding.etLastname.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val confirmPassword = binding.etConfirmPassword.text.toString()

                if (username == "" || firstName == "" || lastName == "" || email == "" || password == "") {
                    showToast("Field harus diisi")
                } else {
                    if (password != confirmPassword) {
                        showToast("Password tidak sesuai")
                    } else {
                        presenter?.register(username, firstName, lastName, email, password)
                    }
                }
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}