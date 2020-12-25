package id.hanifsr.gamedb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.FragmentHomeBinding
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.OnSnapPositionChangeListener
import id.hanifsr.gamedb.util.SnapOnScrollListener
import id.hanifsr.gamedb.util.attachSnapHelperWithListener
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!
	private lateinit var adapter: GameRVAdapter
	private lateinit var homeViewModel: HomeViewModel

	private var games: List<Game> = listOf()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		if (activity != null) {
			showMark(true)
			showRecyclerView()

			homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
			homeViewModel.getGames(getDates()).observe(viewLifecycleOwner, {
				if (it != null) {
					onPopularGamesFetched(it)
				}
			})
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (data != null) {
			if (requestCode == DetailActivity.REQUEST_DELETE && resultCode == DetailActivity.RESULT_DELETE) {
				val name = data.getStringExtra(DetailActivity.EXTRA_NAME)
				Toast.makeText(activity, "$name deleted from Favourites!", Toast.LENGTH_SHORT)
					.show()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun showRecyclerView() {
		binding.rvGames.setHasFixedSize(true)
		binding.rvGames.layoutManager =
			LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
		adapter = GameRVAdapter(listOf()) { game -> showSelectedGame(game) }
		binding.rvGames.adapter = adapter

		val snapHelper = PagerSnapHelper()
		binding.rvGames.attachSnapHelperWithListener(snapHelper,
			SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
			object : OnSnapPositionChangeListener {
				override fun onSnapPositionChange(position: Int) {
					binding.tvTitle.text = games[position].name

					val genreList = mutableListOf<String>()
					for (genre in games[position].genres) {
						genreList.add(genre.name)
					}
					binding.tvGenre.text = genreList.joinToString()
				}
			})
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun getDates(): String {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH) + 1
		val firstDay = "$year-$month-01"
		val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

		return "$firstDay,$today"
	}

	private fun onPopularGamesFetched(games: List<Game>) {
		adapter.updateGames(games)
		this.games = games
		showMark(false)
	}

	private fun showMark(state: Boolean) {
		if (state) {
			binding.pbHome.visibility = View.VISIBLE
			binding.tvPopular.visibility = View.GONE
			binding.tvThisMonth.visibility = View.GONE
		} else {
			binding.pbHome.visibility = View.GONE
			binding.tvPopular.visibility = View.VISIBLE
			binding.tvThisMonth.visibility = View.VISIBLE
		}
	}
}