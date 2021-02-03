package com.mufiid.visitblitar.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.ActivityDetailBinding
import com.mufiid.visitblitar.ui.addreservation.AddReservationActivity
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBinding
    private var tourismEntity: TourismEntity? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var nameTourism: String? = null
    private var priceTicket: Int? = 0

    companion object {
        const val EXTRAS_WISATA = "extras_wisata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        init()
    }

    private fun init() {
        binding.buttonBack.setOnClickListener(this)
        binding.buttonReservation.setOnClickListener(this)
        binding.icMap.setOnClickListener(this)
        tourismEntity = intent.getParcelableExtra(EXTRAS_WISATA)
        setParcelable()
    }

    private fun setParcelable() {
        tourismEntity?.let {
            binding.nameTourist.text = it.nameTouristAttraction
            binding.addressTourist.text = it.address
            binding.descriptionTourist.text = it.description
            binding.priceTicket.text = generateRupiah(it.priceTicket.toString())
            Glide.with(this)
                .load(it.image)
                .error(R.drawable.ic_image_error)
                .into(binding.imageTourist)

            nameTourism = it.nameTouristAttraction
            latitude = it.latitude
            longitude = it.longitude
            priceTicket = it.priceTicket?.toInt()

            // set visibility button reservation
            if (priceTicket == 0) {
                binding.buttonReservation.visibility = View.INVISIBLE
            }

        }
    }

    private fun generateRupiah(nominal: String): String {
        val local = Locale("id", "ID")
        val cursIndonesian: DecimalFormat = NumberFormat.getCurrencyInstance(local) as DecimalFormat
        val symbol: String = Currency.getInstance(local).getSymbol(local)
        cursIndonesian.positivePrefix = symbol

        return cursIndonesian.format(nominal.toDouble())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_back -> finish()
            R.id.button_reservation -> {
                startActivity(Intent(this, AddReservationActivity::class.java).apply {
                    putExtra(AddReservationActivity.EXTRA_WISATA, tourismEntity)
                })
                finish()
            }
            R.id.ic_map -> {
                val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}?q=$nameTourism")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                    setPackage("com.google.android.apps.maps")
                    resolveActivity(packageManager)
                }
                startActivity(mapIntent)
            }
        }
    }
}