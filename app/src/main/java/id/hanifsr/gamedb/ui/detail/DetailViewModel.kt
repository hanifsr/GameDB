package id.hanifsr.gamedb.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.remote.RemoteRepository

class DetailViewModel : ViewModel() {

	private val game = MutableLiveData<Game>()

	fun getDetail(id: Int): LiveData<Game> {
		RemoteRepository.getGameDetail(
			id,
			onSuccess = ::onGameDetailFetched,
			onError = ::onError
		)

		return game
	}

	private fun onGameDetailFetched(game: Game) {
		this.game.postValue(game)
	}

	private fun onError() {
		Log.i("DetailViewModel", "onError: Detail ViewModel")
	}
}