package id.hanifsr.gamedb.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.hanifsr.gamedb.domain.Game

@Entity
data class PopularListEntity(
	@PrimaryKey
	val id: Int,
	val name: String,
	val genres: String,
	val added: Int,
	val rating: Double,
	val ratingTop: Double,
	val backgroundImage: String
)

fun List<PopularListEntity>.asDomainModel(): List<Game> {
	return map {
		Game(
			it.id,
			it.name,
			it.genres,
			"",
			it.rating,
			it.ratingTop,
			"",
			it.backgroundImage,
			"",
			false
		)
	}
}