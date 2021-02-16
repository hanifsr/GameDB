package id.hanifsr.gamedb.data.source.local

import androidx.lifecycle.LiveData
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.data.source.local.room.GDBDao

class LocalDataSource private constructor(private val gdbDao: GDBDao) {

	companion object {
		private var INSTANCE: LocalDataSource? = null

		fun getInstance(gdbDao: GDBDao): LocalDataSource =
			INSTANCE ?: LocalDataSource(gdbDao)
	}

	fun getFavouriteGames(): LiveData<List<GameEntity>> = gdbDao.getFavouriteGames()

	fun getGameFromFavourites(id: Int): LiveData<GameEntity> = gdbDao.getGameFromFavourites(id)

	suspend fun insertGameToFavourites(gameEntity: GameEntity): Long =
		gdbDao.insertGameToFavourites(gameEntity)

	suspend fun deleteGameFromFavourites(gameEntity: GameEntity): Int =
		gdbDao.deleteGameFromFavourites(gameEntity)
}