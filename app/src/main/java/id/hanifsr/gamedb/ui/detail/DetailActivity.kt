package id.hanifsr.gamedb.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R

class DetailActivity : AppCompatActivity() {

	companion object {
		const val EXTRA_TITLE = "extra_title"
		const val EXTRA_GENRE = "extra_genre"
		const val EXTRA_POSTER = "extra_poster"
		const val EXTRA_BANNER = "extra_banner"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		val tvDetailTitle: TextView = findViewById(R.id.tv_detail_title)
		val tvDetailGenre: TextView = findViewById(R.id.tv_detail_genre)
		val ivDetailPoster: ImageView = findViewById(R.id.iv_detail_poster)
		val ivDetailBanner: ImageView = findViewById(R.id.iv_detail_banner)

		val title = intent.getStringExtra(EXTRA_TITLE)
		val genre = intent.getStringExtra(EXTRA_GENRE)
		val poster = intent.getIntExtra(EXTRA_POSTER, 0)
		val banner = intent.getIntExtra(EXTRA_BANNER, 0)

		tvDetailTitle.text = title
		tvDetailGenre.text = genre

		Glide.with(this)
			.load(poster)
			.into(ivDetailPoster)

		Glide.with(this)
			.load(banner)
			.into(ivDetailBanner)

		supportActionBar?.title = title
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.elevation = 0F
	}
}