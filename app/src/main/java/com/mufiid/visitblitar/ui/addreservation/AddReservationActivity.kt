@file:Suppress("DEPRECATION")

package com.mufiid.visitblitar.ui.addreservation

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.ActivityAddReservationBinding
import com.mufiid.visitblitar.ui.dialog.DialogMessageFragment
import com.mufiid.visitblitar.utils.pref.AuthPref
import com.mufiid.visitblitar.utils.pref.UserPref
import com.mufiid.visitblitar.view.AddReservationView
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AddReservationActivity : AppCompatActivity(), AddReservationView, View.OnClickListener, DatePickerDialog.OnDateSetListener{
    private lateinit var binding: ActivityAddReservationBinding
    private lateinit var presenter: AddReservationPresenter
    private var priceTicket: Int? = 0
    private var totalPrice: Int? = 0
    private var tourismId: Int? = 0

    companion object {
        const val EXTRA_WISATA = "extras_wisata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        checkIsLogin()
        init()
        getTotalPrice()
        setDateField()
        getParcelable()
    }

    private fun checkIsLogin(): Boolean {
        return if (!AuthPref.isLoggedIn(this)) {
            showDialogMessage(getString(R.string.warning), getString(R.string.please_login))
            true
        } else {
            false
        }
    }

    private fun setDateField() {
        binding.etDate.setOnFocusChangeListener { _, b ->
            if (b) showCalendar()
        }
    }

    private fun getTotalPrice() {
        binding.etTotalVisitor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var visitor = 0
                if (!cs.isNullOrEmpty()) {
                    visitor = cs.toString().toInt()
                }
                totalPrice = visitor * priceTicket!!

                binding.totalPrice.text = generateRupiah(totalPrice.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun init() {
        presenter = AddReservationPresenter(this)
        binding.btnReservation.setOnClickListener(this)

        binding.totalPrice.text = getString(R.string.rupiah, "0")
        binding.buttonBack.setOnClickListener {
            finish()
        }

    }

    private fun getParcelable() {
        val data = intent.getParcelableExtra<TourismEntity>(EXTRA_WISATA)

        data?.let {
            binding.nameTourist.text = it.nameTouristAttraction
            val price = it.priceTicket?.let { it1 -> generateRupiah(it1) }
            binding.priceTicket.text = getString(R.string.harga_tiket, price)
            Glide.with(this)
                .load(it.image)
                .error(R.drawable.ic_image_error)
                .into(binding.imageTourist)

            priceTicket = it.priceTicket?.toInt()
            tourismId = it.id
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        finish()
        return true
    }

    override fun messageAddReservation(message: String?) {
        showToast(message)
        binding.btnReservation.visibility = View.VISIBLE
    }

    override fun loading(state: Boolean) {
        if (state) {
            binding.btnReservation.visibility = View.GONE
            binding.progressBarAddReservation.visibility = View.VISIBLE
        } else {
            binding.btnReservation.visibility = View.VISIBLE
            binding.progressBarAddReservation.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reservation -> {
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val noTelp = binding.etTelp.text.toString()
                val nik = binding.etNik.text.toString()
                val date = binding.etDate.text.toString()
                val totalVisitor = binding.etTotalVisitor.text.toString()

                if (name == "" || email == "" || noTelp == "" || nik == "" || totalVisitor == "") {
                    showToast("Field harus diisi")
                } else {
                    if (!checkIsLogin()) {
                        UserPref.getUserData(this)?.id?.let {
                            presenter.addReservation(
                                it, name, tourismId, email, date, totalVisitor.toInt(), nik, noTelp
                            )
                        }
                    }

                }

            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun generateRupiah(nominal: String): String {
        val local = Locale("id", "ID")
        val cursIndonesian: DecimalFormat = NumberFormat.getCurrencyInstance(local) as DecimalFormat
        val symbol: String = Currency.getInstance(local).getSymbol(local)
        cursIndonesian.positivePrefix = symbol

        return cursIndonesian.format(nominal.toDouble())
    }

    override fun onDateSet(v: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date = calendar.time

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val dateStr = dateFormat.format(date)
        binding.etDate.setText(dateStr)
    }

    private fun showCalendar() {
        val calender = Calendar.getInstance()
        val datePick = DatePickerDialog(
            this, this,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )

        datePick.show()
    }

    private fun showDialogMessage(title: String, message: String) {
        val mBundle = Bundle().apply {
            putString(DialogMessageFragment.TITLE, title)
            putString(DialogMessageFragment.MESSAGE, message)
        }

        DialogMessageFragment().apply {
            arguments = mBundle
            isCancelable = false
        }.show(
            supportFragmentManager, DialogMessageFragment::class.java.simpleName
        )
    }

    internal var buttonListener: DialogMessageFragment.ButtonListener = object : DialogMessageFragment.ButtonListener {
        override fun choose() {
            finish()
        }
    }
}