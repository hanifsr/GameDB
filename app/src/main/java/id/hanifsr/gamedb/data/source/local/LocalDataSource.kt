package id.hanifsr.gamedb.data.source.local

import id.hanifsr.gamedb.data.source.local.entity.GameEntity
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

	suspend fun getFavouriteGames(): Result<List<GameEntity>> =
		safeDbCall { gdbDao.getFavouriteGames() }

	suspend fun getGameFromFavourites(id: Int): Result<GameEntity> =
		safeDbCall { gdbDao.getGameFromFavourites(id) }

	suspend fun insertGameToFavourites(gameEntity: GameEntity): Result<Long> =
		safeDbCall { gdbDao.insertGameToFavourites(gameEntity) }

	suspend fun deleteGameFromFavourites(gameEntity: GameEntity): Result<Int> =
		safeDbCall { gdbDao.deleteGameFromFavourites(gameEntity) }

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