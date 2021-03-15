package id.hanifsr.gamedb.data.source.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.hanifsr.gamedb.data.source.local.entity.PopularDetailEntity
import id.hanifsr.gamedb.domain.Game

@JsonClass(generateAdapter = true)
data class GameDetailDTO(
	val id: Int?,
	val name: String?,
	val released: String?,
	@Json(name = "background_image")
	val backgroundImage: String?,
	val rating: Double?,
	@Json(name = "rating_top")
	val ratingTop: Double?,
	val developers: List<Developer?>?,
	val genres: List<Genre?>?,
	@Json(name = "description_raw")
	val descriptionRaw: String?
) {
	@JsonClass(generateAdapter = true)
	data class Developer(
		val id: Int?,
		val name: String?
	)

	@JsonClass(generateAdapter = true)
	data class Genre(
		val id: Int?,
		val name: String?
	)
}

fun GameDetailDTO.asDomainModel(): Game {
	return Game(
		id ?: 0,
		name ?: "",
		genres?.joinToString { genre -> genre?.name ?: "" } ?: "",
		released ?: "",
		rating ?: 0.0,
		ratingTop ?: 0.0,
		developers?.joinToString { developer -> developer?.name ?: "" } ?: "",
		backgroundImage ?: "",
		descriptionRaw ?: "",
		false
	)
}

fun GameDetailDTO.asDatabaseEntity(): PopularDetailEntity {
	return PopularDetailEntity(
		id ?: 0,
		name ?: "",
		genres?.joinToString { genre -> genre?.name ?: "" } ?: "",
		released ?: "",
		rating ?: 0.0,
		ratingTop ?: 0.0,
		developers?.joinToString { developer -> developer?.name ?: "" } ?: "",
		backgroundImage ?: "",
		descriptionRaw ?: ""
	)
}