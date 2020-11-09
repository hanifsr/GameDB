package id.hanifsr.gamedb.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.GameData
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private var list: ArrayList<Game> = arrayListOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		rv_games.setHasFixedSize(true)

		list.addAll(GameData.data)
		showRecyclerView()

		supportActionBar?.elevation = 0F
	}

	private fun showRecyclerView() {
		rv_games.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		val adapter = GameRVAdapter(list)
		rv_games.adapter = adapter

		rv_games.addItemDecoration(ItemDecoration(screenWidth()))

		adapter.setOnItemClickCallback(object : OnItemClickCallback {
			override fun onItemClicked(game: Game) {
				showSelectedGame(game)
			}
		})

		val snapHelper = PagerSnapHelper()
		rv_games.attachSnapHelperWithListener(snapHelper,
			SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
			object : OnSnapPositionChangeListener {
				override fun onSnapPositionChange(position: Int) {
					tv_title.text = list[position].title
					tv_genre.text = list[position].genre
				}
			})
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(this@MainActivity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_GAME, game)
		startActivity(intent)
	}

	private fun screenWidth(): Int {
		val displayMetrics = DisplayMetrics()
		windowManager.defaultDisplay.getMetrics(displayMetrics)

		return displayMetrics.widthPixels
	}
}