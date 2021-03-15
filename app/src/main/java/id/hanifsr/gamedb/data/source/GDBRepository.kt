package id.hanifsr.gamedb.data.source

import id.hanifsr.gamedb.data.source.local.LocalDataSource
import id.hanifsr.gamedb.data.source.local.entity.PopularDetailEntity
import id.hanifsr.gamedb.data.source.local.entity.PopularListEntity
import id.hanifsr.gamedb.data.source.local.entity.asDomainModel
import id.hanifsr.gamedb.data.source.remote.RemoteDataSource
import id.hanifsr.gamedb.data.source.remote.response.asDatabaseEntity
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
		var errorResult = Result.Error()
		val dbData = when (val dbResult = localDataSource.getPopularGames()) {
			is Result.Success -> dbResult.data
			is Result.Error -> {
				errorResult = Result.Error(dbResult.cause, dbResult.code, dbResult.errorMessage)
				emptyList()
			}
			else -> emptyList()
		}
		val apiData = when (val apiResult =
			RemoteDataSource.getPopularGames(Util.getFirstAndCurrentDate())) {
			is Result.Success -> apiResult.data.asDatabaseEntity()
			is Result.Error -> {
				errorResult = Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
				emptyList()
			}
			else -> emptyList()
		}

		return if (dbData.isEmpty()) {
			if (apiData.isEmpty()) {
				errorResult
			} else {
				getFromNetworkThenSaveToDatabase(apiData)
			}
		} else {
			if (apiData.isEmpty()) {
				Result.Success(dbData.asDomainModel())
			} else {
				if (Util.isListEqual(dbData, apiData)) {
					Result.Success(dbData.asDomainModel())
				} else {
					getFromNetworkThenSaveToDatabase(apiData)
				}
			}
		}
	}

	override suspend fun getGameDetail(id: Int): Result<Game> {
		return when (val favouriteDbResult = localDataSource.getGameFromFavourites(id)) {
			is Result.Success -> Result.Success(favouriteDbResult.data.asDomainModel())
			else -> {
				when (val popularDbResult = localDataSource.getGameFromPopular(id)) {
					is Result.Success -> Result.Success(popularDbResult.data.asDomainModel())
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

	private suspend fun getFromNetworkThenSaveToDatabase(apiData: List<PopularListEntity>): Result<List<Game>> {
		val clearPopularListEntityAffectedRow =
			when (val clear = localDataSource.clearPopularListEntity()) {
				is Result.Success -> clear.data
				else -> 0
			}
		val clearPopularDetailEntityAffectedRow =
			when (val clear = localDataSource.clearPopularDetailEntity()) {
				is Result.Success -> clear.data
				else -> 0
			}
		return when (val insertResult = localDataSource.insertGamesToPopularList(apiData)) {
			is Result.Success -> {
				val detailList = mutableListOf<PopularDetailEntity>()
				insertResult.data.forEach {
					when (val apiDetailResult = RemoteDataSource.getGameDetail(it.toInt())) {
						is Result.Success -> detailList.add(apiDetailResult.data.asDatabaseEntity())
						else -> detailList.add(
							PopularDetailEntity(
								it.toInt(),
								"",
								"",
								"",
								0.0,
								0.0,
								"", "",
								""
							)
						)
					}
				}
				when (val insertDetailResult =
					localDataSource.insertGamesToPopularDetail(detailList)) {
					is Result.Success -> {
						when (val newDbResult = localDataSource.getPopularGames()) {
							is Result.Success -> Result.Success(newDbResult.data.asDomainModel())
							is Result.Error -> Result.Error(
								newDbResult.cause,
								newDbResult.code,
								newDbResult.errorMessage
							)
							else -> Result.Error()
						}
					}
					is Result.Error -> Result.Error(
						insertDetailResult.cause,
						insertDetailResult.code,
						insertDetailResult.errorMessage
					)
					else -> Result.Error()
				}
			}
			is Result.Error -> Result.Error(
				insertResult.cause,
				insertResult.code,
				insertResult.errorMessage
			)
			else -> Result.Error()
		}
	}
}