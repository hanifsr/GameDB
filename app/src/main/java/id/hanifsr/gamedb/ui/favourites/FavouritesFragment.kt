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

	private var _binding: FragmentFavouritesBinding? = null
	private val binding get() = _binding!!
	private lateinit var adapter: FavouritesRVAdapter
	private lateinit var viewModel: FavouritesViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentFavouritesBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			initRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			viewModel =
				ViewModelProvider(this, factory).get(FavouritesViewModel::class.java)
			viewModel.favouriteGames.observe(viewLifecycleOwner, {
				when (it) {
					is Result.Loading -> showMark(loading = true, false, null)
					is Result.Success -> onFavouriteGamesFetched(it.data)
					is Result.Empty -> showMark(loading = false, false, null)
					is Result.Error -> showMark(loading = false, true, it.errorMessage)
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
			}
			showMark(loading = false, false, null)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun initRecyclerView() {
		binding.rvFavourites.setHasFixedSize(true)
		adapter = FavouritesRVAdapter { game, position ->
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
		showMark(loading = false, false, null)
	}

	private fun showMark(loading: Boolean, error: Boolean, message: String?) {
		if (loading) {
			binding.pbFavourites.visibility = View.VISIBLE
			binding.ivStatusFavourites.visibility = View.GONE
			binding.tvStatusFavourites.visibility = View.GONE
			binding.tvFavouritesText.visibility = View.GONE
		} else {
			binding.pbFavourites.visibility = View.GONE
			if (error) {
				binding.ivStatusFavourites.visibility = View.VISIBLE
				binding.tvStatusFavourites.visibility = View.VISIBLE
				binding.tvStatusFavourites.text = message
				binding.tvFavouritesText.visibility = View.GONE
			} else {
				if (adapter.itemCount > 0) {
					binding.tvFavouritesText.visibility = View.GONE
				} else {
					binding.tvFavouritesText.visibility = View.VISIBLE
				}
			}
		}
	}
}