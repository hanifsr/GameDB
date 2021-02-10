package id.hanifsr.gamedb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

class SearchViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	val keyword = MutableLiveData("")
	val searchGames: LiveData<List<GameEntity>> = Transformations.switchMap(keyword) {
		gdbRepository.searchGames(it)
	}
}