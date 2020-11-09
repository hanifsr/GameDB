package id.hanifsr.gamedb.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

	companion object {
		const val EXTRA_GAME = "extra_game"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		val game = intent.getParcelableExtra<Game>(EXTRA_GAME) as Game

		tv_detail_title.text = game.title
		tv_detail_genre.text = game.genre

		Glide.with(this)
			.load(game.poster)
			.into(iv_detail_poster)

		Glide.with(this)
			.load(game.banner)
			.into(iv_detail_banner)

		supportActionBar?.title = title
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.elevation = 0F
	}
}