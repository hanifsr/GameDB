package id.hanifsr.gamedb.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.GameData
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.*

class MainActivity : AppCompatActivity() {

	private lateinit var rvGames: RecyclerView
	private var list: ArrayList<Game> = arrayListOf()

	private lateinit var tvTitle: TextView
	private lateinit var tvGenre: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		rvGames = findViewById(R.id.rv_games)
		rvGames.setHasFixedSize(true)

		tvTitle = findViewById(R.id.tv_title)
		tvGenre = findViewById(R.id.tv_genre)

		list.addAll(GameData.data)
		showRecyclerView()

		supportActionBar?.elevation = 0F
	}

	private fun showRecyclerView() {
		rvGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		val adapter = GameRVAdapter(list)
		rvGames.adapter = adapter

		rvGames.addItemDecoration(ItemDecoration(screenWidth()))

		adapter.setOnItemClickCallback(object : OnItemClickCallback {
			override fun onItemClicked(game: Game) {
				showSelectedGame(game)
			}
		})

		val snapHelper = PagerSnapHelper()
		rvGames.attachSnapHelperWithListener(snapHelper,
			SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
			object : OnSnapPositionChangeListener {
				override fun onSnapPositionChange(position: Int) {
					tvTitle.text = list[position].title
					tvGenre.text = list[position].genre
				}
			})
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(this@MainActivity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_TITLE, game.title)
		startActivity(intent)
	}

	private fun screenWidth(): Int {
		val displayMetrics = DisplayMetrics()
		windowManager.defaultDisplay.getMetrics(displayMetrics)

		return displayMetrics.widthPixels
	}
}