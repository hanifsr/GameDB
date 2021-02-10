package id.hanifsr.gamedb.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.data.source.remote.RemoteDataSource
import id.hanifsr.gamedb.data.source.remote.response.GameDetailResponse
import id.hanifsr.gamedb.data.source.remote.response.GameListResponse
import id.hanifsr.gamedb.util.MappingHelper

class GDBRepository private constructor() : GDBDataSource {

	companion object {
		@Volatile
		private var INSTANCE: GDBRepository? = null

		fun getInstance(): GDBRepository = INSTANCE ?: synchronized(this) {
			INSTANCE ?: GDBRepository()
		}
	}

	private lateinit var gameEntities: MutableLiveData<List<GameEntity>>
	private lateinit var gameEntity: MutableLiveData<GameEntity>

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
		gameEntity = MutableLiveData()
		RemoteDataSource.getGameDetail(
			id,
			::onSuccess,
			::onError
		)

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

	override fun getFavouriteGames(): LiveData<List<GameEntity>> {
		return MutableLiveData(emptyList())
	}

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