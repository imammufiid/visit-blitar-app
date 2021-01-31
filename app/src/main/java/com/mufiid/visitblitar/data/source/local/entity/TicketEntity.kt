package com.mufiid.visitblitar.data.source.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketEntity(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("total_visitors")
	val totalVisitors: Int? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("tourism_id")
	val tourismId: Int? = null,

	@field:SerializedName("status_is_coming")
	val statusIsComing: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("no_telp")
	val noTelp: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("name_tourist_attraction")
	val nameTouristAttraction: String? = null,

	@field:SerializedName("code_reservation")
	val codeReservation: String? = null,

	@field:SerializedName("img_code_reservation")
	val imgCodeReservation: String? = null,
) : Parcelable
