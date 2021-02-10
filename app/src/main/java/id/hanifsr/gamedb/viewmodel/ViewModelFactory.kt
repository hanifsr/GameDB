package id.hanifsr.gamedb.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.di.Injection
import id.hanifsr.gamedb.ui.detail.DetailViewModel
import id.hanifsr.gamedb.ui.favourites.FavouritesViewModel
import id.hanifsr.gamedb.ui.home.HomeViewModel
import id.hanifsr.gamedb.ui.search.SearchViewModel

class ViewModelFactory private constructor(private val gdbRepository: GDBRepository) :
	ViewModelProvider.NewInstanceFactory() {

	companion object {
		@Volatile
		private var INSTANCE: ViewModelFactory? = null

		fun getInstance(context: Context): ViewModelFactory = INSTANCE ?: synchronized(this) {
			INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
		}
	}

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return when {
			modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
				HomeViewModel(gdbRepository) as T
			}
			modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
				DetailViewModel(gdbRepository) as T
			}
			modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
				SearchViewModel(gdbRepository) as T
			}
			modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> {
				FavouritesViewModel(gdbRepository) as T
			}
			else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
		}
	}
}