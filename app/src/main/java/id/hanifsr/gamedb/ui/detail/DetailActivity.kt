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
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.ActivityDetailBinding
import id.hanifsr.gamedb.util.MappingHelper
import id.hanifsr.gamedb.viewmodel.ViewModelFactory
import java.util.*

class DetailActivity : AppCompatActivity() {

	companion object {
		const val EXTRA_ID = "extra_id"
		const val EXTRA_POSITION = "extra_position"
		const val EXTRA_NAME = "extra_name"
		const val REQUEST_DELETE = 200
		const val RESULT_DELETE = 201
	}

	private lateinit var activityDetailBinding: ActivityDetailBinding
	private lateinit var genreRVAdapter: GenreRVAdapter
	private lateinit var detailViewModel: DetailViewModel
	private lateinit var gameEntity: GameEntity

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
		activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(activityDetailBinding.root)

		showMark(true)

		val id = intent.getIntExtra(EXTRA_ID, 0)
		val position = intent.getIntExtra(EXTRA_POSITION, -1)

		showRecyclerViewGenre()

		val factory = ViewModelFactory.getInstance(this)
		detailViewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
		detailViewModel.getDetail(id).observe(this, {
			if (it != null) {
				gameDetailFetched(it)
				gameEntity = it
			}
		})

		activityDetailBinding.lbDetailFavourite.setOnLikeListener(object : OnLikeListener {
			override fun liked(likeButton: LikeButton?) {
				detailViewModel.insertToFavourites(gameEntity).observe(this@DetailActivity, {
					if (it > 0) {
						Toast.makeText(
							this@DetailActivity,
							"${gameEntity.name} is added to Favourites!",
							Toast.LENGTH_SHORT
						).show()
					} else {
						Toast.makeText(
							this@DetailActivity,
							"Failed to add ${gameEntity.name} to Favourites!",
							Toast.LENGTH_SHORT
						).show()
					}
				})
			}

			override fun unLiked(likeButton: LikeButton?) {
				detailViewModel.deleteFromFavourites(gameEntity).observe(this@DetailActivity, {
					if (it > 0) {
						val intent = Intent()
						intent.putExtra(EXTRA_POSITION, position)
						intent.putExtra(EXTRA_NAME, gameEntity.name)
						setResult(RESULT_DELETE, intent)
						finish()
					} else {
						Toast.makeText(
							this@DetailActivity,
							"Failed to remove ${gameEntity.name} from Favourites",
							Toast.LENGTH_SHORT
						).show()
					}
				})
			}
		})
	}

	private fun showRecyclerViewGenre() {
		activityDetailBinding.rvDetailGenre.setHasFixedSize(true)
		activityDetailBinding.rvDetailGenre.layoutManager =
			LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		genreRVAdapter = GenreRVAdapter(emptyList())
		activityDetailBinding.rvDetailGenre.adapter = genreRVAdapter
	}

	private fun gameDetailFetched(gameEntity: GameEntity) {
		Glide.with(this)
			.load(gameEntity.backgroundImage)
			.into(activityDetailBinding.ivDetailBanner)

		activityDetailBinding.tvDetailTitle.text = gameEntity.name

		genreRVAdapter.updateGenre(gameEntity.genres.split(", "))

		activityDetailBinding.tvDetailReleased.text = MappingHelper.dateFormat(gameEntity.released)

		activityDetailBinding.tvDetailDevelopers.text = gameEntity.developers

		activityDetailBinding.tvDetailDescription.text = gameEntity.description

		activityDetailBinding.lbDetailFavourite.isLiked = gameEntity.isFavourite

		setTitleActionBar(gameEntity.name)

		showMark(false)
	}

	private fun setTitleActionBar(title: String) {
		supportActionBar?.title = title
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.elevation = 0F
	}

	private fun showMark(state: Boolean) {
		if (state) {
			activityDetailBinding.pbDetail.visibility = View.VISIBLE
			activityDetailBinding.tvDetailReleasedText.visibility = View.GONE
			activityDetailBinding.tvDetailDevelopersText.visibility = View.GONE
			activityDetailBinding.lbDetailFavourite.visibility = View.GONE
		} else {
			activityDetailBinding.pbDetail.visibility = View.GONE
			activityDetailBinding.tvDetailReleasedText.visibility = View.VISIBLE
			activityDetailBinding.tvDetailDevelopersText.visibility = View.VISIBLE
			activityDetailBinding.lbDetailFavourite.visibility = View.VISIBLE
		}
	}
}