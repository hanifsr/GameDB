package id.hanifsr.gamedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.vo.Result
import kotlinx.coroutines.launch

class DetailViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	private val gameDetail = MutableLiveData<Result<Game>>()

	init {
		gameDetail.value = Result.Loading
	}

	fun getDetail(id: Int): LiveData<Result<Game>> {
		viewModelScope.launch {
			gameDetail.value = gdbRepository.getGameDetail(id)
		}

		return gameDetail
	}

	fun insertToFavourites(game: Game): LiveData<Result<Long>> {
		val id = MutableLiveData<Result<Long>>()
		viewModelScope.launch {
			id.value = gdbRepository.insertGameToFavourites(game)
		}

		return id
	}

	fun deleteFromFavourites(game: Game): LiveData<Result<Int>> {
		val affectedRow = MutableLiveData<Result<Int>>()
		viewModelScope.launch {
			affectedRow.value = gdbRepository.deleteGameFromFavourites(game)
		}

		return affectedRow
	}
}