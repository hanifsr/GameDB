package id.hanifsr.gamedb.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.FragmentFavouritesBinding
import id.hanifsr.gamedb.ui.detail.DetailActivity

class FavouritesFragment : Fragment() {

	private var _binding: FragmentFavouritesBinding? = null
	private val binding get() = _binding!!
	private lateinit var adapter: FavouritesRVAdapter
	private lateinit var favouritesViewModel: FavouritesViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentFavouritesBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		if (activity != null) {
			showMark(true)
			showRecyclerView()

			favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
			favouritesViewModel.favouriteGames().observe(viewLifecycleOwner, {
				if (it != null) {
					onFavouriteGamesFetched(it)
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
				adapter.removeItem(position)
				Toast.makeText(activity, "$name deleted from Favourites!", Toast.LENGTH_SHORT)
					.show()
				if (adapter.itemCount == 0) {
					binding.tvFavouritesText.visibility = View.VISIBLE
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun showRecyclerView() {
		binding.rvFavourites.setHasFixedSize(true)
		binding.rvFavourites.layoutManager = LinearLayoutManager(activity)
		adapter = FavouritesRVAdapter(arrayListOf()) { game, position ->
			showSelectedGame(
				game,
				position
			)
		}
		binding.rvFavourites.adapter = adapter
	}

	private fun showSelectedGame(game: Game, position: Int) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		intent.putExtra(DetailActivity.EXTRA_POSITION, position)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onFavouriteGamesFetched(games: List<Game>) {
		adapter.updateFavouriteRVData(games)
		showMark(false)
	}

	private fun showMark(state: Boolean) {
		if (state) {
			binding.pbFavourites.visibility = View.VISIBLE
		} else {
			binding.pbFavourites.visibility = View.GONE
			if (adapter.itemCount > 0) {
				binding.tvFavouritesText.visibility = View.GONE
			} else {
				binding.tvFavouritesText.visibility = View.VISIBLE
			}
		}
	}
}