package com.mufiid.visitblitar.ui.reservation

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.ItemTouristReservationBinding
import com.mufiid.visitblitar.ui.addreservation.AddReservationActivity
import com.mufiid.visitblitar.ui.detail.DetailActivity
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class ReservationAdapter(val data: List<TourismEntity>): RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationAdapter.ViewHolder {
        val itemTouristReservationBinding = ItemTouristReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemTouristReservationBinding)
    }

    override fun onBindViewHolder(holder: ReservationAdapter.ViewHolder, position: Int) {
        data[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemTouristReservationBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun bind(wisata: TourismEntity) {
            val res = itemView.resources
            with(binding) {
                Glide.with(itemView.context)
                    .load(wisata.image)
                    .error(R.drawable.ic_image_error)
                    .into(imageTourist)

                name.text = wisata.nameTouristAttraction
                htm.text = res.getString(R.string.htm_tourist_attraction, generateRupiah(wisata.priceTicket.toString()))
                capacity.text = res.getString(R.string.capasity_tourist_attraction, wisata.capasity)
                visitors.text = res.getString(R.string.visitors_tourist_attraction, wisata.totalVisitorToday)
                container.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(AddReservationActivity.EXTRA_WISATA, wisata)
                    }
                    itemView.context.startActivity(intent)
                }
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
}