package id.hanifsr.gamedb.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.remote.RemoteRepository
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.OnSnapPositionChangeListener
import id.hanifsr.gamedb.util.SnapOnScrollListener
import id.hanifsr.gamedb.util.attachSnapHelperWithListener
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

	private lateinit var adapter: GameRVAdapter

	private val TAG = MainActivity::class.java.simpleName
	private var games: List<Game> = listOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		rv_games.setHasFixedSize(true)

		showRecyclerView()

		RemoteRepository.getPopularGames(
			getDates(),
			onSuccess = ::onPopularGamesFetched,
			onError = ::onError
		)

		supportActionBar?.elevation = 0F
	}

	private fun showRecyclerView() {
		rv_games.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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
		val intent = Intent(this@MainActivity, DetailActivity::class.java)
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

	private fun onError() {
		Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
	}
}