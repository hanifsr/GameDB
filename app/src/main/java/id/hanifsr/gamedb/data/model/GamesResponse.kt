package id.hanifsr.gamedb.data.model

data class GamesResponse(
	val count: Int,
	val next: String,
	val previous: String,
	val results: List<Game>
)