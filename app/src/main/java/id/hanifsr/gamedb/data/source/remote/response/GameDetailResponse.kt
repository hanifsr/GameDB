package id.hanifsr.gamedb.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
	@SerializedName("id")
	val id: Int?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("released")
	val released: String?,
	@SerializedName("background_image")
	val backgroundImage: String?,
	@SerializedName("rating")
	val rating: Double?,
	@SerializedName("rating_top")
	val ratingTop: Double?,
	@SerializedName("developers")
	val developers: List<Developer?>?,
	@SerializedName("genres")
	val genres: List<Genre?>?,
	@SerializedName("description_raw")
	val descriptionRaw: String?
) {
	data class Developer(
		@SerializedName("id")
		val id: Int?,
		@SerializedName("name")
		val name: String?,
	)

	data class Genre(
		@SerializedName("id")
		val id: Int?,
		@SerializedName("name")
		val name: String?,
	)
}