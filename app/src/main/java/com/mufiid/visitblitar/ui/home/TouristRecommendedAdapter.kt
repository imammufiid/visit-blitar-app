package com.mufiid.visitblitar.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.ItemTouristAttractionRecommendationBinding
import com.mufiid.visitblitar.ui.addreservation.AddReservationActivity
import com.mufiid.visitblitar.ui.detail.DetailActivity

class TouristRecommendedAdapter(val data: List<TourismEntity>?): RecyclerView.Adapter<TouristRecommendedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristRecommendedAdapter.ViewHolder {
        val itemsTourismRecommendedBinding = ItemTouristAttractionRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsTourismRecommendedBinding)
    }

    override fun onBindViewHolder(holder: TouristRecommendedAdapter.ViewHolder, position: Int) {
        data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    inner class ViewHolder(private val binding: ItemTouristAttractionRecommendationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(wisata: TourismEntity) {
            var reqOptions = RequestOptions().apply {
                transform(CenterCrop(), RoundedCorners(10))
            }
            with(binding) {
                Glide.with(itemView.context)
                    .load(wisata.image)
                    .error(R.drawable.ic_image_error)
                    .apply(reqOptions)
                    .into(imageTourist)
                nameTouristAttraction.text = wisata.nameTouristAttraction
                addressTouristAttraction.text = wisata.address
                btnDetail.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRAS_WISATA, wisata)
                    }
                    itemView.context.startActivity(intent)
                }
                container.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRAS_WISATA, wisata)
                    }
                    itemView.context.startActivity(intent)
                }

            }
        }
    }
}