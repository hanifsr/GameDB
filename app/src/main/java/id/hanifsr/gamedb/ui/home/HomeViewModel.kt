package id.hanifsr.gamedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

class HomeViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	val popularGames: LiveData<List<GameEntity>> = gdbRepository.getPopularGames()
}