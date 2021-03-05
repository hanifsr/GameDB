package id.hanifsr.gamedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.vo.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	private val _popularGames = MutableLiveData<Result<List<Game>>>()
	val popularGames: LiveData<Result<List<Game>>>
		get() = _popularGames

	init {
		_popularGames.value = Result.Loading
		viewModelScope.launch {
			_popularGames.value = gdbRepository.getPopularGames()
		}
	}
}