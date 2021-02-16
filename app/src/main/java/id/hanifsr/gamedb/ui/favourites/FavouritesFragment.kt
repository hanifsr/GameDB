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
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.FragmentFavouritesBinding
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.viewmodel.ViewModelFactory

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
		/*fragmentFavouritesBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)*/
		_fragmentFavouritesBinding = FragmentFavouritesBinding.inflate(inflater, container, false)
		return fragmentFavouritesBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			showMark(true)
			showRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			favouritesViewModel =
				ViewModelProvider(this, factory).get(FavouritesViewModel::class.java)
			favouritesViewModel.favouriteGames.observe(viewLifecycleOwner, {
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
				favouritesRVAdapter.removeItem(position)
				Toast.makeText(activity, "$name deleted from Favourites!", Toast.LENGTH_SHORT)
					.show()
				if (favouritesRVAdapter.itemCount == 0) {
					fragmentFavouritesBinding.tvFavouritesText.visibility = View.VISIBLE
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_fragmentFavouritesBinding = null
	}

	private fun showRecyclerView() {
		fragmentFavouritesBinding.rvFavourites.setHasFixedSize(true)
		fragmentFavouritesBinding.rvFavourites.layoutManager = LinearLayoutManager(activity)
		favouritesRVAdapter = FavouritesRVAdapter(emptyList()) { gameEntity, position ->
			showSelectedGame(
				gameEntity,
				position
			)
		}
		fragmentFavouritesBinding.rvFavourites.adapter = favouritesRVAdapter
	}

	private fun showSelectedGame(gameEntity: GameEntity, position: Int) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, gameEntity.id)
		intent.putExtra(DetailActivity.EXTRA_POSITION, position)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onFavouriteGamesFetched(gameEntities: List<GameEntity>) {
		favouritesRVAdapter.updateFavouriteRVData(gameEntities)
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