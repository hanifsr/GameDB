package id.hanifsr.gamedb.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouritesViewModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is Favourites Fragment"
	}
	val text: LiveData<String> = _text
}