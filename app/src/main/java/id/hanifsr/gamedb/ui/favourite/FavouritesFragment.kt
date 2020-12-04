package id.hanifsr.gamedb.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.hanifsr.gamedb.R
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment : Fragment() {

	private lateinit var favouritesViewModel: FavouritesViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_favourites, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
		favouritesViewModel.text.observe(viewLifecycleOwner, Observer {
			tv_favourites.text = it
		})

	}
}