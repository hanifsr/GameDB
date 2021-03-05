package id.hanifsr.gamedb.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.hanifsr.gamedb.databinding.FragmentFavouritesBinding
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.viewmodel.ViewModelFactory
import id.hanifsr.gamedb.vo.Result

class FavouritesFragment : Fragment() {

	private var _fragmentFavouritesBinding: FragmentFavouritesBinding? = null
	private val fragmentFavouritesBinding get() = _fragmentFavouritesBinding!!
	private lateinit var favouritesRVAdapter: FavouritesRVAdapter
	private lateinit var favouritesViewModel: FavouritesViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_fragmentFavouritesBinding = FragmentFavouritesBinding.inflate(inflater, container, false)
		return fragmentFavouritesBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			initRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			favouritesViewModel =
				ViewModelProvider(this, factory).get(FavouritesViewModel::class.java)
			favouritesViewModel.favouriteGames.observe(viewLifecycleOwner, {
				when (it) {
					is Result.Loading -> showMark(true)
					is Result.Success -> onFavouriteGamesFetched(it.data)
					is Result.Empty -> showMark(false)
					is Result.Error -> Toast.makeText(activity, it.errorMessage, Toast.LENGTH_LONG)
						.show()
				}
			})
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (data != null) {
			if (requestCode == DetailActivity.REQUEST_DELETE && resultCode == DetailActivity.RESULT_DELETE) {
				val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, -1)
				val name = data.getStringExtra(DetailActivity.EXTRA_NAME)
				favouritesRVAdapter.removeItem(position)
				Toast.makeText(activity, "$name deleted from Favourites!", Toast.LENGTH_SHORT)
					.show()
			}
			showMark(false)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_fragmentFavouritesBinding = null
	}

	private fun initRecyclerView() {
		fragmentFavouritesBinding.rvFavourites.setHasFixedSize(true)
		favouritesRVAdapter = FavouritesRVAdapter { game, position ->
			showSelectedGame(
				game,
				position
			)
		}
		fragmentFavouritesBinding.rvFavourites.adapter = favouritesRVAdapter
	}

	private fun showSelectedGame(game: Game, position: Int) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		intent.putExtra(DetailActivity.EXTRA_POSITION, position)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onFavouriteGamesFetched(games: List<Game>) {
		favouritesRVAdapter.updateFavouriteRVData(games)
		showMark(false)
	}

	private fun showMark(state: Boolean) {
		if (state) {
			fragmentFavouritesBinding.pbFavourites.visibility = View.VISIBLE
		} else {
			fragmentFavouritesBinding.pbFavourites.visibility = View.GONE
			if (favouritesRVAdapter.itemCount > 0) {
				fragmentFavouritesBinding.tvFavouritesText.visibility = View.GONE
			} else {
				fragmentFavouritesBinding.tvFavouritesText.visibility = View.VISIBLE
			}
		}
	}
}