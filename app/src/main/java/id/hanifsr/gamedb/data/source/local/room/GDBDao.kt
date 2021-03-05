package id.hanifsr.gamedb.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

@Dao
interface GDBDao {

	@Query("SELECT * FROM gameentity")
	suspend fun getFavouriteGames(): List<GameEntity>

	@Query("SELECT * FROM gameentity WHERE id = :id")
	suspend fun getGameFromFavourites(id: Int): GameEntity

	@Insert
	suspend fun insertGameToFavourites(gameEntity: GameEntity): Long

	@Delete
	suspend fun deleteGameFromFavourites(gameEntity: GameEntity): Int
}