package id.hanifsr.gamedb.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.vo.Result
import kotlinx.coroutines.launch

class FavouritesViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	private val _favouriteGames = MutableLiveData<Result<List<Game>>>()
	val favouriteGames: LiveData<Result<List<Game>>>
		get() = _favouriteGames

	init {
		_favouriteGames.value = Result.Loading
		viewModelScope.launch {
			_favouriteGames.value = gdbRepository.getFavouriteGames()
		}
	}
}