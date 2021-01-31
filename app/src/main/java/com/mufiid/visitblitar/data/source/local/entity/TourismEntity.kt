package com.mufiid.visitblitar.data.source.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TourismEntity(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("capasity")
	val capasity: Int? = null,

	@field:SerializedName("price_ticket")
	val priceTicket: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("total_visitor_today")
	val totalVisitorToday: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name_tourist_attraction")
	val nameTouristAttraction: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
) : Parcelable
