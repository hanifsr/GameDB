package id.hanifsr.gamedb.data.model

data class Game(
	val id: Int,
	val name: String,
	val genres: List<Genre>,
	val released: String,
	val background_image: String,
	val publishers: List<Publisher>,
	val description_raw: String
)