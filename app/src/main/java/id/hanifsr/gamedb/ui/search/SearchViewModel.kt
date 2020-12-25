package id.hanifsr.gamedb.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.remote.RemoteRepository

class SearchViewModel : ViewModel() {

	private val games = MutableLiveData<List<Game>>()
	var keyword = ""
		set(value) {
			field = value
			searchGames()
		}

	fun searchGames(): LiveData<List<Game>> {
		RemoteRepository.searchGames(
			keyword,
			onSuccess = ::onSearchGamesSucceed,
			onError = ::onSearchGamesFailed
		)

		return games
	}

	private fun onSearchGamesSucceed(games: List<Game>) {
		this.games.postValue(games)
	}

	private fun onSearchGamesFailed() {
		Log.i("GameDBLog", "onSearchGameFailed: SearchViewModel")
	}
}