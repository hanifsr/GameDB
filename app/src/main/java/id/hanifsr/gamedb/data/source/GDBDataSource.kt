package id.hanifsr.gamedb.data.source

import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.vo.Result

interface GDBDataSource {
	suspend fun getPopularGames(): Result<List<Game>>
	suspend fun getGameDetail(id: Int): Result<Game>
	suspend fun searchGames(keyword: String): Result<List<Game>>
	suspend fun getFavouriteGames(): Result<List<Game>>
	suspend fun insertGameToFavourites(game: Game): Result<Long>
	suspend fun deleteGameFromFavourites(game: Game): Result<Int>
}