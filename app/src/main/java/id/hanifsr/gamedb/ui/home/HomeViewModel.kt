package id.hanifsr.gamedb.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.remote.RemoteRepository

class HomeViewModel : ViewModel() {

	private val games = MutableLiveData<List<Game>>()

	fun getGames(dates: String): LiveData<List<Game>> {
		RemoteRepository.getPopularGames(
			dates,
			onSuccess = ::onPopularGamesFetched,
			onError = ::onError
		)

		return games
	}

	private fun onPopularGamesFetched(games: List<Game>) {
		this.games.postValue(games)
	}

	private fun onError() {
		Log.i("HomeViewModel", "onError: Home Fragment ViewModel")
	}
}