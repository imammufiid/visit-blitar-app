package com.mufiid.visitblitar.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ticketentities")
data class TicketEntity(
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "ticket_id")
	@field:SerializedName("id")
	val id: Int? = null,

	@ColumnInfo(name = "tourism_id")
	@field:SerializedName("tourism_id")
	val tourismId: Int? = null,

	@ColumnInfo(name = "user_id")
	@field:SerializedName("user_id")
	val userId: Int? = null,

	@ColumnInfo(name = "name_tourist_attraction")
	@field:SerializedName("name_tourist_attraction")
	val nameTouristAttraction: String? = null,

	@ColumnInfo(name = "name")
	@field:SerializedName("name")
	val name: String? = null,

	@ColumnInfo(name = "email")
	@field:SerializedName("email")
	val email: String? = null,

	@ColumnInfo(name = "nik")
	@field:SerializedName("nik")
	val nik: String? = null,

	@ColumnInfo(name = "no_telp")
	@field:SerializedName("no_telp")
	val noTelp: String? = null,

	@ColumnInfo(name = "code_reservation")
	@field:SerializedName("code_reservation")
	val codeReservation: String? = null,

	@ColumnInfo(name = "date")
	@field:SerializedName("date")
	val date: String? = null,

	@ColumnInfo(name = "total_visitors")
	@field:SerializedName("total_visitors")
	val totalVisitors: Int? = null,

	@ColumnInfo(name = "status_is_coming")
	@field:SerializedName("status_is_coming")
	val statusIsComing: Int? = null,

	@ColumnInfo(name = "created_at")
	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@ColumnInfo(name = "updated_at")
	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@ColumnInfo(name = "img_code_reservation")
	@field:SerializedName("img_code_reservation")
	val imgCodeReservation: String? = null,
) : Parcelable
