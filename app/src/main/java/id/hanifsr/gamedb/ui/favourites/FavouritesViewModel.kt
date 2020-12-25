package id.hanifsr.gamedb.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.local.GameHelper
import id.hanifsr.gamedb.util.MappingHelper

class FavouritesViewModel : ViewModel() {
	private val games = MutableLiveData<List<Game>>()

	fun favouriteGames(): LiveData<List<Game>> {
		val cursor = GameHelper.getInstance()?.queryAll()
		this.games.postValue(MappingHelper.mapCursorToArrayList(cursor))

		return games
	}
}