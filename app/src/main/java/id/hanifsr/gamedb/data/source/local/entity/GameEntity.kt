package id.hanifsr.gamedb.data.source.local.entity

data class GameEntity(
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
