package id.hanifsr.gamedb.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.like.LikeButton
import com.like.OnLikeListener
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.databinding.ActivityDetailBinding
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.viewmodel.ViewModelFactory
import id.hanifsr.gamedb.vo.Result
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
	private lateinit var genreRVAdapter: GenreRVAdapter
	private lateinit var viewModel: DetailViewModel
	private lateinit var game: Game

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

		val id = intent.getIntExtra(EXTRA_ID, 0)
		val position = intent.getIntExtra(EXTRA_POSITION, -1)

		initRecyclerViewGenre()

		val factory = ViewModelFactory.getInstance(this)
		viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
		viewModel.getDetail(id).observe(this, {
			when (it) {
				is Result.Loading -> showMark(loading = true, false, null)
				is Result.Success -> gameDetailFetched(it.data)
				is Result.Empty -> showMark(loading = false, true, "Game is empty!")
				is Result.Error -> showMark(loading = false, true, it.errorMessage)
			}
		})

		binding.lbDetailFavourite.setOnLikeListener(object : OnLikeListener {
			override fun liked(likeButton: LikeButton?) {
				viewModel.insertToFavourites(game).observe(this@DetailActivity, {
					when (it) {
						is Result.Success -> Toast.makeText(
							this@DetailActivity,
							"${game.name} is added to Favourites!",
							Toast.LENGTH_SHORT
						).show()
						else -> Toast.makeText(
							this@DetailActivity,
							"Failed to add ${game.name} to Favourites!",
							Toast.LENGTH_SHORT
						).show()
					}
				})
			}

			override fun unLiked(likeButton: LikeButton?) {
				viewModel.deleteFromFavourites(game).observe(this@DetailActivity, {
					when (it) {
						is Result.Success -> {
							val intent = Intent()
							intent.putExtra(EXTRA_POSITION, position)
							intent.putExtra(EXTRA_NAME, game.name)
							setResult(RESULT_DELETE, intent)
							finish()
						}
						else -> Toast.makeText(
							this@DetailActivity,
							"Failed to remove ${game.name} from Favourites",
							Toast.LENGTH_SHORT
						).show()
					}
				})
			}
		})
	}

	private fun initRecyclerViewGenre() {
		binding.rvDetailGenre.setHasFixedSize(true)
		genreRVAdapter = GenreRVAdapter()
		binding.rvDetailGenre.adapter = genreRVAdapter
	}

	private fun gameDetailFetched(game: Game) {
		binding.game = game
		genreRVAdapter.genres = game.genres.split(", ")
		this.game = game
		setTitleActionBar(game.name)
		showMark(loading = false, false, null)
	}

	private fun setTitleActionBar(title: String) {
		supportActionBar?.title = title
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.elevation = 0F
	}

	private fun showMark(loading: Boolean, error: Boolean, message: String?) {
		if (loading) {
			binding.pbDetail.visibility = View.VISIBLE
			binding.ivStatusDetail.visibility = View.GONE
			binding.tvStatusDetail.visibility = View.GONE
			binding.tvDetailReleasedText.visibility = View.GONE
			binding.tvDetailDevelopersText.visibility = View.GONE
			binding.lbDetailFavourite.visibility = View.GONE
		} else {
			binding.pbDetail.visibility = View.GONE
			if (error) {
				binding.ivStatusDetail.visibility = View.VISIBLE
				binding.tvStatusDetail.visibility = View.VISIBLE
				binding.tvStatusDetail.text = message
				binding.tvDetailReleasedText.visibility = View.GONE
				binding.tvDetailDevelopersText.visibility = View.GONE
				binding.lbDetailFavourite.visibility = View.GONE
			} else {
				binding.tvDetailReleasedText.visibility = View.VISIBLE
				binding.tvDetailDevelopersText.visibility = View.VISIBLE
				binding.lbDetailFavourite.visibility = View.VISIBLE
			}

		}
	}
}