package id.hanifsr.gamedb.data.source

import id.hanifsr.gamedb.data.source.local.LocalDataSource
import id.hanifsr.gamedb.data.source.local.entity.asDomainModel
import id.hanifsr.gamedb.data.source.remote.RemoteDataSource
import id.hanifsr.gamedb.data.source.remote.response.asDomainModel
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.domain.asDatabaseEntity
import id.hanifsr.gamedb.util.Util
import id.hanifsr.gamedb.vo.Result

class GDBRepository private constructor(
	private val localDataSource: LocalDataSource
) : GDBDataSource {

	companion object {
		@Volatile
		private var INSTANCE: GDBRepository? = null

		fun getInstance(localDataSource: LocalDataSource): GDBRepository =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?: GDBRepository(localDataSource)
			}
	}

	override suspend fun getPopularGames(): Result<List<Game>> {
		return when (val apiResult =
			RemoteDataSource.getPopularGames(Util.getFirstAndCurrentDate())) {
			is Result.Success -> Result.Success(apiResult.data.asDomainModel())
			is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
			else -> Result.Error()
		}
	}

	override suspend fun getGameDetail(id: Int): Result<Game> {
		return when (val dbResult = localDataSource.getGameFromFavourites(id)) {
			is Result.Success -> Result.Success(dbResult.data.asDomainModel())
			else -> {
				when (val apiResult = RemoteDataSource.getGameDetail(id)) {
					is Result.Success -> Result.Success(apiResult.data.asDomainModel())
					is Result.Error -> Result.Error(
						apiResult.cause,
						apiResult.code,
						apiResult.errorMessage
					)
					else -> Result.Error()
				}
			}
		}
	}

	override suspend fun searchGames(keyword: String): Result<List<Game>> {
		return when (val apiResult = RemoteDataSource.searchGames(keyword)) {
			is Result.Success -> Result.Success(apiResult.data.asDomainModel())
			is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
			else -> Result.Error()
		}
	}

	override suspend fun getFavouriteGames(): Result<List<Game>> {
		return when (val dbResult = localDataSource.getFavouriteGames()) {
			is Result.Success -> Result.Success(dbResult.data.asDomainModel())
			is Result.Error -> Result.Error(dbResult.cause, dbResult.code, dbResult.errorMessage)
			else -> Result.Error()
		}
	}

	override suspend fun insertGameToFavourites(game: Game): Result<Long> {
		return when (val dbResult =
			localDataSource.insertGameToFavourites(game.asDatabaseEntity())) {
			is Result.Success -> Result.Success(dbResult.data)
			is Result.Error -> Result.Error(dbResult.cause, dbResult.code, dbResult.errorMessage)
			else -> Result.Error()
		}
	}

	override suspend fun deleteGameFromFavourites(game: Game): Result<Int> {
		return when (val dbResult =
			localDataSource.deleteGameFromFavourites(game.asDatabaseEntity())) {
			is Result.Success -> Result.Success(dbResult.data)
			is Result.Error -> Result.Error(dbResult.cause, dbResult.code, dbResult.errorMessage)
			else -> Result.Error()
		}
	}
}