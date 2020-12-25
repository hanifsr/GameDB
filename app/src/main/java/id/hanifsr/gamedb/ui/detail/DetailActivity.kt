package id.hanifsr.gamedb.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

	companion object {
		const val EXTRA_ID = "extra_id"
		const val EXTRA_POSITION = "extra_position"
		const val EXTRA_NAME = "extra_name"
		const val REQUEST_DELETE = 200
		const val RESULT_DELETE = 201
	}

	private lateinit var binding: ActivityDetailBinding
	private lateinit var adapter: GenreRVAdapter
	private lateinit var detailViewModel: DetailViewModel
	private lateinit var game: Game

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		showMark(true)

		val id = intent.getIntExtra(EXTRA_ID, 0)
		val position = intent.getIntExtra(EXTRA_POSITION, -1)

		showRecyclerViewGenre()

		detailViewModel = ViewModelProvider(
			this,
			ViewModelProvider.NewInstanceFactory()
		).get(DetailViewModel::class.java)
		detailViewModel.getDetail(id).observe(this, {
			if (it != null) {
				game = it
				gameDetailFetched(it)
			}
		})

		detailViewModel.isFavourite().observe(this, {
			binding.lbDetailFavourite.isLiked = it
		})

		binding.lbDetailFavourite.setOnLikeListener(object : OnLikeListener {
			override fun liked(likeButton: LikeButton?) {
				val result = detailViewModel.insertToFavourite(game)
				if (result != null && result > 0) {
					Toast.makeText(
						this@DetailActivity,
						"${game.name} is added to Favourites!",
						Toast.LENGTH_SHORT
					).show()
				} else {
					Toast.makeText(
						this@DetailActivity,
						"Failed adding ${game.name} to Favourites!",
						Toast.LENGTH_SHORT
					).show()
				}
			}

			override fun unLiked(likeButton: LikeButton?) {
				val result = detailViewModel.deleteFromFavourite(id)
				if (result != null && result > 0) {
					val intent = Intent()
					intent.putExtra(EXTRA_POSITION, position)
					intent.putExtra(EXTRA_NAME, game.name)
					setResult(RESULT_DELETE, intent)
					finish()
				} else {
					Toast.makeText(
						this@DetailActivity,
						"Failed deleting ${game.name} from Favourites!",
						Toast.LENGTH_SHORT
					).show()
				}
			}
		})
	}

	private fun showRecyclerViewGenre() {
		binding.rvDetailGenre.setHasFixedSize(true)
		binding.rvDetailGenre.layoutManager =
			LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		adapter = GenreRVAdapter(listOf())
		binding.rvDetailGenre.adapter = adapter
	}

	private fun gameDetailFetched(game: Game) {
		Glide.with(this)
			.load(game.background_image)
			.into(binding.ivDetailBanner)

		binding.tvDetailTitle.text = game.name

		adapter.updateGenre(game.genres)

		binding.tvDetailReleased.text = dateFormat(game.released)

		val publisherList = mutableListOf<String>()
		for (publisher in game.publishers) {
			publisherList.add(publisher.name)
		}
		binding.tvDetailPublisher.text = publisherList.joinToString()

		binding.tvDetailDescription.text = game.description_raw

		setTitleActionBar(game.name)

		showMark(false)
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

	private fun showMark(state: Boolean) {
		if (state) {
			binding.pbDetail.visibility = View.VISIBLE
			binding.tvDetailReleasedText.visibility = View.GONE
			binding.tvDetailPublisherText.visibility = View.GONE
			binding.lbDetailFavourite.visibility = View.GONE
		} else {
			binding.pbDetail.visibility = View.GONE
			binding.tvDetailReleasedText.visibility = View.VISIBLE
			binding.tvDetailPublisherText.visibility = View.VISIBLE
			binding.lbDetailFavourite.visibility = View.VISIBLE
		}
	}
}