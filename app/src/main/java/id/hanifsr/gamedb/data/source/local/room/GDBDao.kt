package id.hanifsr.gamedb.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

@Dao
interface GDBDao {

	@Query("SELECT * FROM gameentity")
	fun getFavouriteGames(): LiveData<List<GameEntity>>

	@Query("SELECT * FROM gameentity WHERE id = :id")
	fun getGameFromFavourites(id: Int): LiveData<GameEntity>

	@Insert
	suspend fun insertGameToFavourites(gameEntity: GameEntity): Long

	@Delete
	suspend fun deleteGameFromFavourites(gameEntity: GameEntity): Int
}