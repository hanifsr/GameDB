package id.hanifsr.gamedb.ui.detail

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.source.remote.RemoteRepository
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

	companion object {
		const val EXTRA_ID = "extra_id"
	}

	private lateinit var adapter: GenreRVAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		val id = intent.getIntExtra(EXTRA_ID, 0)

		rv_detail_genre.setHasFixedSize(true)

		showRecyclerViewGenre()

		RemoteRepository.getGameDetail(
			id,
			onSuccess = ::gameDetailFetched,
			onError = ::onError
		)
	}

	private fun showRecyclerViewGenre() {
		rv_detail_genre.layoutManager =
			LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		adapter = GenreRVAdapter(listOf())
		rv_detail_genre.adapter = adapter
	}

	private fun gameDetailFetched(game: Game) {
		Glide.with(this)
			.load(game.background_image)
			.into(iv_detail_banner)

		tv_detail_title.text = game.name

		adapter.updateGenre(game.genres)

		val publisherList = mutableListOf<String>()
		for (publisher in game.publishers) {
			publisherList.add(publisher.name)
		}
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			tv_detail_publisher.text = Html.fromHtml(
				getString(R.string.publisher, publisherList.joinToString()),
				FROM_HTML_MODE_LEGACY
			)
			tv_detail_released.text = Html.fromHtml(
				getString(R.string.released, dateFormat(game.released)),
				FROM_HTML_MODE_LEGACY
			)
		} else {
			tv_detail_publisher.text =
				Html.fromHtml(getString(R.string.publisher, publisherList.joinToString()))
			tv_detail_released.text =
				Html.fromHtml(getString(R.string.released, dateFormat(game.released)))
		}

		tv_detail_description.text = game.description_raw

		setTitleActionBar(game.name)
	}

	private fun onError() {
		Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
	}

	private fun setTitleActionBar(title: String) {
		supportActionBar?.title = title
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.elevation = 0F
	}

	private fun dateFormat(unformattedDate: String?): String {
		return if (unformattedDate == null) {
			"TBA"
		} else {
			val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(unformattedDate)
			if (date == null) {
				"TBA"
			} else {
				SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
			}
		}
	}
}