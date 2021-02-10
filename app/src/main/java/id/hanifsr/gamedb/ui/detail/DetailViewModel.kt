package id.hanifsr.gamedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

class DetailViewModel(private val gdbRepository: GDBRepository) : ViewModel() {

	fun getDetail(id: Int): LiveData<GameEntity> = gdbRepository.getGameDetail(id)
}