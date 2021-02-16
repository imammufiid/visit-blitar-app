@file:Suppress("DEPRECATION")

package com.mufiid.visitblitar.ui.detailticket

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.databinding.ActivityDetailTicketBinding
import com.mufiid.visitblitar.utils.pref.UserPref
import com.mufiid.visitblitar.view.DetailTicketView

@Suppress("DEPRECATION")
class DetailTicketActivity : AppCompatActivity(), DetailTicketView, View.OnClickListener {
    private lateinit var binding: ActivityDetailTicketBinding
    private lateinit var presenter: DetailTicketPresenter
    private lateinit var loading: ProgressDialog
    private var codeReservation: String? = null
    private var position = 0
    private var data: TicketEntity? = null

    companion object {
        const val EXTRA_RESERVATION = "extras_reservation"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_COMING = 100
        const val RESULT_COMING = 101
        const val REQUEST_GO_HOME = 200
        const val RESULT_GO_HOME = 202
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        getParcelable()
    }

    private fun getParcelable() {
        data = intent.getParcelableExtra<TicketEntity>(EXTRA_RESERVATION)
        position = intent.getIntExtra(EXTRA_POSITION, 0)
        codeReservation = data?.codeReservation

        presenter.getTicketById(data?.id)
    }

    private fun init() {
        presenter = DetailTicketPresenter(this)
        loading = ProgressDialog(this)
        binding.btnIsComing.setOnClickListener(this)
    }

    override fun getDetailTicket(message: String?, data: TicketEntity?) {
        if (data != null) {
            Glide.with(this)
                .load(data.imgCodeReservation)
                .error(R.drawable.ic_image_error)
                .into(binding.imageQrCode)

            if (data.statusIsComing == 1) {
                binding.btnIsComing.text = getString(R.string.goodbye)
            } else if (data.statusIsComing == 2) {
                binding.btnIsComing.visibility = View.GONE
            }
        }
    }

    override fun message(message: String?) {
        showToast(message)
        finish()
    }

    override fun loading(state: Boolean) {
        if (state) {
            loading.let {
                it.setMessage(getString(R.string.please_wait))
                it.show()
            }
        } else {
            loading.let {
                it.dismiss()
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_is_coming -> {
//                val intent = Intent().apply {
//                    putExtra(EXTRA_RESERVATION, data)
//                    putExtra(EXTRA_POSITION, position)
//                }
                UserPref.getUserData(this)?.id?.let { userId ->
                    codeReservation?.let { codeReservation ->
                        presenter.isComing(
                            userId, codeReservation
                        )
                    }
                }

//                if (data?.statusIsComing == 0) {
//                    setResult(RESULT_COMING, intent)
//                } else if (data?.statusIsComing == 1) {
//                    setResult(RESULT_GO_HOME, intent)
//                }
                finish()
            }
        }
    }
}