package com.mufiid.visitblitar.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.ActivityDetailBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    companion object {
        const val EXTRAS_WISATA = "extras_wisata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val data = intent.getParcelableExtra<TourismEntity>(EXTRAS_WISATA)

        if (data != null) {
            binding.nameTourist.text = data.nameTouristAttraction
            binding.addressTourist.text = data.address
            binding.descriptionTourist.text = data.description
            binding.priceTicket.text = generateRupiah(data.priceTicket.toString())
            Glide.with(this)
                .load(data.image)
                .error(R.drawable.ic_image_error)
                .into(binding.imageTourist)
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun generateRupiah(nominal: String): String {
        val local = Locale("id", "ID")
        val cursIndonesian: DecimalFormat = NumberFormat.getCurrencyInstance(local) as DecimalFormat
        val symbol: String = Currency.getInstance(local).getSymbol(local)
        cursIndonesian.positivePrefix = symbol

        return cursIndonesian.format(nominal.toDouble())
    }
}