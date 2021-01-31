package com.mufiid.visitblitar.data.source.local.entity

import com.google.gson.annotations.SerializedName

data class TourismCategoryEntity(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name_category")
	val nameCategory: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
