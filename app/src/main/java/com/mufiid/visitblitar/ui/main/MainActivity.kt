package com.mufiid.visitblitar.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.databinding.ActivityMainBinding
import com.mufiid.visitblitar.ui.home.HomeFragment
import com.mufiid.visitblitar.ui.reservation.ReservationFragment
import com.mufiid.visitblitar.ui.ticket.TicketFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var doubleBack = false

    companion object {
        const val ACTIVITY_NAME = "main_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        init()
    }

//    private fun checkLogin() {
//        if(!AuthPref.isLoggedIn(this)) {
//            finish()
//        }
//    }

    private fun init() {
        loadFragment(HomeFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                loadFragment(HomeFragment())
                return true
            }
            R.id.nav_my_ticket -> {
                loadFragment(TicketFragment())
                return true
            }
            R.id.nav_reservation -> {
                loadFragment(ReservationFragment())
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        if (doubleBack) {
            super.onBackPressed()
            return
        }

        doubleBack = true
        Toast.makeText(this, "Tekan sekali lagi", Toast.LENGTH_SHORT).show()
        Handler(mainLooper).postDelayed({
            doubleBack = false
        }, 2000)
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
                    ),
                    1
                )
            }
        }
    }

}