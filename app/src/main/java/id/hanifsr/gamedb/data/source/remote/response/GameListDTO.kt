package id.hanifsr.gamedb.data.source.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.hanifsr.gamedb.data.source.local.entity.PopularListEntity
import id.hanifsr.gamedb.domain.Game

@JsonClass(generateAdapter = true)
data class GameListDTO(
	val results: List<Result?>?
) {
	@JsonClass(generateAdapter = true)
	data class Result(
		val name: String?,
		@Json(name = "background_image")
		val backgroundImage: String?,
		val rating: Double?,
		@Json(name = "rating_top")
		val ratingTop: Double?,
		val added: Int?,
		val id: Int?,
		val genres: List<Genre?>?
	) {
		@JsonClass(generateAdapter = true)
		data class Genre(
			val id: Int?,
			val name: String?
		)
	}
}

fun GameListDTO.asDomainModel(): List<Game> {
	return results?.map {
		Game(
			it?.id ?: 0,
			it?.name ?: "",
			it?.genres?.joinToString { genre -> genre?.name ?: "" } ?: "",
			"",
			it?.rating ?: 0.0,
			it?.ratingTop ?: 0.0,
			"",
			it?.backgroundImage ?: "",
			"",
			false
		)
	} ?: emptyList()
}

fun GameListDTO.asDatabaseEntity(): List<PopularListEntity> {
	return results?.map {
		PopularListEntity(
			it?.id ?: 0,
			it?.name ?: "",
			it?.genres?.joinToString { genre -> genre?.name ?: "" } ?: "",
			it?.added ?: 0,
			it?.rating ?: 0.0,
			it?.ratingTop ?: 0.0,
			it?.backgroundImage ?: "",
		)
	} ?: emptyList()
}