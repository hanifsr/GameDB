package id.hanifsr.gamedb.data.source.local

import id.hanifsr.gamedb.data.source.local.entity.FavouriteEntity
import id.hanifsr.gamedb.data.source.local.entity.PopularDetailEntity
import id.hanifsr.gamedb.data.source.local.entity.PopularListEntity
import id.hanifsr.gamedb.data.source.local.room.GDBDao
import id.hanifsr.gamedb.vo.ErrorCause
import id.hanifsr.gamedb.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource private constructor(private val gdbDao: GDBDao) {

	companion object {
		private var INSTANCE: LocalDataSource? = null

		fun getInstance(gdbDao: GDBDao): LocalDataSource =
			INSTANCE ?: LocalDataSource(gdbDao)
	}

	suspend fun getFavouriteGames(): Result<List<FavouriteEntity>> =
		safeDbCall { gdbDao.getFavouriteGames() }

	suspend fun getGameFromFavourites(id: Int): Result<FavouriteEntity> =
		safeDbCall { gdbDao.getGameFromFavourites(id) }

	suspend fun insertGameToFavourites(favouriteEntity: FavouriteEntity): Result<Long> =
		safeDbCall { gdbDao.insertGameToFavourites(favouriteEntity) }

	suspend fun deleteGameFromFavourites(favouriteEntity: FavouriteEntity): Result<Int> =
		safeDbCall { gdbDao.deleteGameFromFavourites(favouriteEntity) }

	suspend fun getPopularGames(): Result<List<PopularListEntity>> =
		safeDbCall { gdbDao.getPopularGames() }

	suspend fun getGameFromPopular(id: Int): Result<PopularDetailEntity> =
		safeDbCall { gdbDao.getGameFromPopular(id) }

	suspend fun clearPopularListEntity(): Result<Int> =
		safeDbCall { gdbDao.clearPopularListEntity() }

	suspend fun clearPopularDetailEntity(): Result<Int> =
		safeDbCall { gdbDao.clearPopularDetailEntity() }

	suspend fun insertGamesToPopularList(popularListEntities: List<PopularListEntity>): Result<List<Long>> =
		safeDbCall { gdbDao.insertGamesToPopularList(popularListEntities) }

	suspend fun insertGamesToPopularDetail(popularDetailEntities: List<PopularDetailEntity>): Result<List<Long>> =
		safeDbCall { gdbDao.insertGamesToPopularDetail(popularDetailEntities) }

	private suspend fun <T> safeDbCall(dbCall: suspend () -> T): Result<T> {
		return withContext(Dispatchers.IO) {
			try {
				val data = dbCall.invoke()
				if (data != null) {
					Result.Success(data)
				} else {
					Result.Empty
				}
			} catch (throwable: Throwable) {
				Result.Error(ErrorCause.DB_ERROR, null, throwable.message)
			}
		}
	}
}