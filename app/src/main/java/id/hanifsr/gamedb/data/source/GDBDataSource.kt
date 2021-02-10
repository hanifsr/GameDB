package id.hanifsr.gamedb.data.source

import androidx.lifecycle.LiveData
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

interface GDBDataSource {

	fun getPopularGames(): LiveData<List<GameEntity>>

	fun getGameDetail(id: Int): LiveData<GameEntity>

	fun searchGames(keyword: String): LiveData<List<GameEntity>>

	fun getFavouriteGames(): LiveData<List<GameEntity>>
}