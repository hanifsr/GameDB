package id.hanifsr.gamedb.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

class FavouritesViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	val favouriteGames: LiveData<List<GameEntity>> = gdbRepository.getFavouriteGames()
}