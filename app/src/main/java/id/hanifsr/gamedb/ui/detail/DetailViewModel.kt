package id.hanifsr.gamedb.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.local.DatabaseContract
import id.hanifsr.gamedb.data.source.local.GameHelper
import id.hanifsr.gamedb.data.source.remote.RemoteRepository
import id.hanifsr.gamedb.util.MappingHelper

class DetailViewModel : ViewModel() {

	private val game = MutableLiveData<Game>()
	private val isFavourite = MutableLiveData<Boolean>()

	fun getDetail(id: Int): LiveData<Game> {
		val cursor = GameHelper.getInstance()?.queryById(id)
		if (cursor != null && cursor.count > 0) {
			onGameDetailFetched(MappingHelper.mapCursorToArrayList(cursor).first())
			cursor.close()
			isFavourite.postValue(true)
		} else {
			RemoteRepository.getGameDetail(
				id,
				onSuccess = ::onGameDetailFetched,
				onError = ::onError
			)
			isFavourite.postValue(false)
		}

		return game
	}

	private fun onGameDetailFetched(game: Game) {
		this.game.postValue(game)
	}

	private fun onError() {
		Log.i("GameDBLog", "onError: DetailViewModel")
	}

	fun insertToFavourite(game: Game): Long? {
		val mValues = ContentValues()
		mValues.put(DatabaseContract.GameColumns.ID, game.id)
		mValues.put(DatabaseContract.GameColumns.NAME, game.name)
		mValues.put(DatabaseContract.GameColumns.GENRES, game.genres.joinToString { it.name })
		mValues.put(DatabaseContract.GameColumns.RELEASED, game.released)
		mValues.put(DatabaseContract.GameColumns.BACKGROUND_IMAGE, game.background_image)
		mValues.put(
			DatabaseContract.GameColumns.PUBLISHERS,
			game.publishers.joinToString { it.name })
		mValues.put(DatabaseContract.GameColumns.DESCRIPTION_RAW, game.description_raw)

		return GameHelper.getInstance()?.insert(mValues)
	}

	fun deleteFromFavourite(id: Int): Int? = GameHelper.getInstance()?.deleteById(id)

	fun isFavourite(): LiveData<Boolean> {
		return isFavourite
	}
}