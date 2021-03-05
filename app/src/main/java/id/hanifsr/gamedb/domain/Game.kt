package id.hanifsr.gamedb.domain

import id.hanifsr.gamedb.data.source.local.entity.GameEntity

data class Game(
	val id: Int,
	val name: String,
	val genres: String,
	val released: String,
	val rating: Double,
	val ratingTop: Double,
	val developers: String,
	val backgroundImage: String,
	val description: String,
	val isFavourite: Boolean
)

fun Game.asDatabaseEntity(): GameEntity {
	return GameEntity(
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