package id.hanifsr.gamedb.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.hanifsr.gamedb.data.source.local.entity.FavouriteEntity
import id.hanifsr.gamedb.data.source.local.entity.PopularDetailEntity
import id.hanifsr.gamedb.data.source.local.entity.PopularListEntity

@Dao
interface GDBDao {

	@Query("SELECT * FROM favouriteentity ORDER BY name ASC")
	suspend fun getFavouriteGames(): List<FavouriteEntity>

	@Query("SELECT * FROM favouriteentity WHERE id = :id")
	suspend fun getGameFromFavourites(id: Int): FavouriteEntity

	@Insert
	suspend fun insertGameToFavourites(favouriteEntity: FavouriteEntity): Long

	@Delete
	suspend fun deleteGameFromFavourites(favouriteEntity: FavouriteEntity): Int

	@Query("SELECT * FROM popularlistentity ORDER BY added DESC")
	suspend fun getPopularGames(): List<PopularListEntity>

	@Query("SELECT * FROM populardetailentity WHERE id = :id")
	suspend fun getGameFromPopular(id: Int): PopularDetailEntity

	@Query("DELETE FROM popularlistentity")
	suspend fun clearPopularListEntity(): Int

	@Query("DELETE FROM populardetailentity")
	suspend fun clearPopularDetailEntity(): Int

	@Insert
	suspend fun insertGamesToPopularList(popularListEntities: List<PopularListEntity>): List<Long>

	@Insert
	suspend fun insertGamesToPopularDetail(popularDetailEntities: List<PopularDetailEntity>): List<Long>
}