package com.mufiid.visitblitar.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.ItemTouristAttractionBinding
import com.mufiid.visitblitar.ui.addreservation.AddReservationActivity

class HomeAdapter(val data: List<TourismEntity>?): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val itemsTourismBinding = ItemTouristAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsTourismBinding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    inner class ViewHolder(private val binding: ItemTouristAttractionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(wisata: TourismEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(wisata.image)
                    .error(R.drawable.ic_image_error)
                    .into(imageTourist)
                nameTouristAttraction.text = wisata.nameTouristAttraction
                addressTouristAttraction.text = wisata.address
                cardViewContainer.setOnClickListener {
                    val intent = Intent(itemView.context, AddReservationActivity::class.java).apply {
                        putExtra(AddReservationActivity.EXTRA_WISATA, wisata)
                    }
                    itemView.context.startActivity(intent)
                }

            }
        }
    }
}