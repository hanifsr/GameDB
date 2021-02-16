package id.hanifsr.gamedb.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import id.hanifsr.gamedb.data.source.local.LocalDataSource
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.data.source.remote.RemoteDataSource
import id.hanifsr.gamedb.data.source.remote.response.GameDetailResponse
import id.hanifsr.gamedb.data.source.remote.response.GameListResponse
import id.hanifsr.gamedb.util.MappingHelper

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

	private lateinit var gameEntities: MutableLiveData<List<GameEntity>>
	private val gameEntity = MediatorLiveData<GameEntity>()

	override fun getPopularGames(): LiveData<List<GameEntity>> {
		gameEntities = MutableLiveData()
		RemoteDataSource.getPopularGames(
			MappingHelper.getFirstAndCurrentDate(),
			::onSuccess,
			::onError
		)

		return gameEntities
	}

	override fun getGameDetail(id: Int): LiveData<GameEntity> {
		val favouriteGame = localDataSource.getGameFromFavourites(id)
		gameEntity.addSource(favouriteGame) {
			gameEntity.removeSource(favouriteGame)
			if (it == null) {
				RemoteDataSource.getGameDetail(
					id,
					::onSuccess,
					::onError
				)
			} else {
				gameEntity.addSource(favouriteGame) { newData ->
					gameEntity.postValue(newData)
				}
			}
		}

		return gameEntity
	}

	override fun searchGames(keyword: String): LiveData<List<GameEntity>> {
		gameEntities = MutableLiveData()
		RemoteDataSource.searchGames(
			keyword,
			::onSuccess,
			::onError
		)

		return gameEntities
	}

	override fun getFavouriteGames(): LiveData<List<GameEntity>> =
		localDataSource.getFavouriteGames()

	override suspend fun insertGameToFavourites(gameEntity: GameEntity): Long {
		gameEntity.isFavourite = true
		return localDataSource.insertGameToFavourites(gameEntity)
	}

	override suspend fun deleteGameFromFavourites(gameEntity: GameEntity): Int =
		localDataSource.deleteGameFromFavourites(gameEntity)

	private fun onSuccess(gameListResponse: GameListResponse?) {
		gameEntities.postValue(MappingHelper.gameListResponseToGameEntitiesMapper(gameListResponse))
	}

	private fun onSuccess(gameDetailResponse: GameDetailResponse?) {
		gameEntity.postValue(MappingHelper.gameDetailResponseToGameEntityMapper(gameDetailResponse))
	}

	private fun onError(message: String) {
		Log.i("GDBLog", "onError: $message")
		gameEntities.postValue(MappingHelper.gameListResponseToGameEntitiesMapper(null))
		gameEntity.postValue(MappingHelper.gameDetailResponseToGameEntityMapper(null))
	}
}