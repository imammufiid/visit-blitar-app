package com.mufiid.visitblitar.ui.detailticket

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.databinding.ActivityDetailTicketBinding
import com.mufiid.visitblitar.utils.pref.UserPref
import com.mufiid.visitblitar.view.DetailTicketView

class DetailTicketActivity : AppCompatActivity(), DetailTicketView, View.OnClickListener {
    private lateinit var binding: ActivityDetailTicketBinding
    private lateinit var presenter: DetailTicketPresenter
    private lateinit var loading: ProgressDialog
    private var codeReservation: String? = null

    companion object {
        const val EXTRA_RESERVATION = "extras_reservation"
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
        val data = intent.getParcelableExtra<TicketEntity>(EXTRA_RESERVATION)
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

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_is_coming -> {
                UserPref.getUserData(this)?.id?.let { userId ->
                    codeReservation?.let { codeReservation ->
                        presenter.isComing(
                            userId, codeReservation
                        )
                    }
                }
            }
        }
    }
}