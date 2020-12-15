package id.hanifsr.gamedb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.OnSnapPositionChangeListener
import id.hanifsr.gamedb.util.SnapOnScrollListener
import id.hanifsr.gamedb.util.attachSnapHelperWithListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

	private lateinit var adapter: GameRVAdapter
	private lateinit var homeViewModel: HomeViewModel

	private var games: List<Game> = listOf()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		if (activity != null) {
			showRecyclerView()

			homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
			homeViewModel.getGames(getDates()).observe(viewLifecycleOwner, {
				if (it != null) {
					onPopularGamesFetched(it)
				}
			})
		}
	}

	private fun showRecyclerView() {
		rv_games.setHasFixedSize(true)
		rv_games.layoutManager =
			LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
		adapter = GameRVAdapter(listOf()) { game -> showSelectedGame(game) }
		rv_games.adapter = adapter

		val snapHelper = PagerSnapHelper()
		rv_games.attachSnapHelperWithListener(snapHelper,
			SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
			object : OnSnapPositionChangeListener {
				override fun onSnapPositionChange(position: Int) {
					tv_title.text = games[position].name

					val genreList = mutableListOf<String>()
					for (genre in games[position].genres) {
						genreList.add(genre.name)
					}
					tv_genre.text = genreList.joinToString()
				}
			})
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivity(intent)
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
	}
}