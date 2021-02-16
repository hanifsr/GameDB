package id.hanifsr.gamedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	fun getDetail(id: Int): LiveData<GameEntity> = gdbRepository.getGameDetail(id)

	fun insertToFavourites(gameEntity: GameEntity): LiveData<Long> {
		val id = MutableLiveData<Long>()
		viewModelScope.launch {
			id.postValue(gdbRepository.insertGameToFavourites(gameEntity))
		}

		return id
	}

	fun deleteFromFavourites(gameEntity: GameEntity): LiveData<Int> {
		val affectedRow = MutableLiveData<Int>()
		viewModelScope.launch {
			affectedRow.postValue(gdbRepository.deleteGameFromFavourites(gameEntity))
		}

		return affectedRow
	}
}