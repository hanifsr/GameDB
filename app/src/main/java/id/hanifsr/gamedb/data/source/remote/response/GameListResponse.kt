package id.hanifsr.gamedb.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class GameListResponse(
	@SerializedName("results")
	val results: List<Result?>?,
) {
	data class Result(
		@SerializedName("name")
		val name: String?,
		@SerializedName("background_image")
		val backgroundImage: String?,
		@SerializedName("rating")
		val rating: Double?,
		@SerializedName("rating_top")
		val ratingTop: Double?,
		@SerializedName("id")
		val id: Int?,
		@SerializedName("genres")
		val genres: List<Genre?>?
	) {
		data class Genre(
			@SerializedName("id")
			val id: Int?,
			@SerializedName("name")
			val name: String?,
		)
	}
}