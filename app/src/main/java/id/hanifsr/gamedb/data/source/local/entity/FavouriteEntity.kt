package id.hanifsr.gamedb.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.hanifsr.gamedb.domain.Game

@Entity
data class FavouriteEntity(
	@PrimaryKey
	val id: Int,
	val name: String,
	val genres: String,
	val released: String,
	val rating: Double,
	val ratingTop: Double,
	val developers: String,
	val backgroundImage: String,
	val description: String
)

fun List<FavouriteEntity>.asDomainModel(): List<Game> {
	return map {
		Game(
			it.id,
			it.name,
			it.genres,
			it.released,
			it.rating,
			it.ratingTop,
			it.developers,
			it.backgroundImage,
			it.description,
			true
		)
	}
}

fun FavouriteEntity.asDomainModel(): Game {
	return Game(
		id,
		name,
		genres,
		released,
		rating,
		ratingTop,
		developers,
		backgroundImage,
		description,
		true
	)
}