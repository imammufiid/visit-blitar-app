package com.mufiid.visitblitar.ui.scanqr

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.databinding.ActivityScanQrBinding
import com.mufiid.visitblitar.ui.main.MainActivity
import com.mufiid.visitblitar.view.ScanQrView

class ScanQrActivity : AppCompatActivity(), ScanQrView {
    private lateinit var binding: ActivityScanQrBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var loading: ProgressDialog
    private lateinit var presenter: ScanQrPresenter
    private var activity: String? = null

    companion object {
        const val EXTRAS_ACTIVITY = "extras_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        init()
        scanner()
        setParcelable()
    }

    private fun init() {
        loading = ProgressDialog(this)
        presenter = ScanQrPresenter(this)
    }

    private fun scanner() {
        codeScanner = CodeScanner(this, binding.scannerView).apply {
            camera = CodeScanner.CAMERA_BACK
            formats = listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false


            // decode qr
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    when (activity) {
                        // activity from main activity
                        MainActivity.ACTIVITY_NAME -> {
                            // scan
                            presenter.scanQr()
                        }

                        // write code from other activity at above this comment

                    }
                }
            }

            // error exception
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Toast.makeText(this@ScanQrActivity, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG).show()
                }
            }

        }

        binding.scannerView.setOnClickListener { codeScanner.startPreview() }
    }

    private fun setParcelable() {
        activity = intent.getStringExtra(EXTRAS_ACTIVITY)
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun result(status: Int?, message: String?) {
        Toast.makeText(this@ScanQrActivity, message,
            Toast.LENGTH_LONG).show()
    }

    override fun loading(state: Boolean) {
        if (state) {
            loading?.let {
                it.setMessage(getString(R.string.please_wait))
                it.show()
            }
        } else {
            loading?.let {
                it.dismiss()
            }
        }
    }
}