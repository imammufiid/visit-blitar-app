package com.mufiid.visitblitar.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tourismentities")
data class TourismEntity(

	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "tourism_id")
	@field:SerializedName("id")
	val id: Int? = null,

	@ColumnInfo(name = "category_id")
	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@ColumnInfo(name = "name_tourist_attraction")
	@field:SerializedName("name_tourist_attraction")
	val nameTouristAttraction: String? = null,

	@ColumnInfo(name = "address")
	@field:SerializedName("address")
	val address: String? = null,

	@ColumnInfo(name = "capacity")
	@field:SerializedName("capasity")
	val capasity: Int? = null,

	@ColumnInfo(name = "price_ticket")
	@field:SerializedName("price_ticket")
	val priceTicket: String? = null,

	@ColumnInfo(name = "total_visitor_today")
	@field:SerializedName("total_visitor_today")
	val totalVisitorToday: Int? = null,

	@ColumnInfo(name = "description")
	@field:SerializedName("description")
	val description: String? = null,

	@ColumnInfo(name = "image")
	@field:SerializedName("image")
	val image: String? = null,

	@ColumnInfo(name = "latitude")
	@field:SerializedName("latitude")
	val latitude: String? = null,

	@ColumnInfo(name = "longitude")
	@field:SerializedName("longitude")
	val longitude: String? = null
) : Parcelable
