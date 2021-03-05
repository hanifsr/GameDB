package id.hanifsr.gamedb.ui.search

import androidx.lifecycle.*
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.vo.Result
import kotlinx.coroutines.launch

class SearchViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	val keyword = MutableLiveData("")
	private val _searchGames = MutableLiveData<Result<List<Game>>>()
	val searchGames: LiveData<Result<List<Game>>> = Transformations.switchMap(keyword) {
		viewModelScope.launch {
			_searchGames.value = gdbRepository.searchGames(it)
		}
		_searchGames
	}

	init {
		_searchGames.value = Result.Loading
	}
}