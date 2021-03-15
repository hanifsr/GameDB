package id.hanifsr.gamedb.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.hanifsr.gamedb.domain.Game

@Entity
data class PopularDetailEntity(
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

fun PopularDetailEntity.asDomainModel(): Game {
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
		false
	)
}