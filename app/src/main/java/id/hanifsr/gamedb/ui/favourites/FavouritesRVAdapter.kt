package id.hanifsr.gamedb.ui.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.ItemGamesVerticalBinding

class FavouritesRVAdapter(
	private var games: ArrayList<Game>,
	private val onItemClick: (game: Game, position: Int) -> Unit
) : RecyclerView.Adapter<FavouritesRVAdapter.FavouriteRVHolder>() {

	fun updateFavouriteRVData(games: List<Game>) {
		this.games = games as ArrayList<Game>
		notifyDataSetChanged()
	}

	fun removeItem(position: Int) {
		games.removeAt(position)
		notifyItemRemoved(position)
		notifyItemRangeChanged(position, games.size)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRVHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_games_vertical, parent, false)
		return FavouriteRVHolder(view)
	}

	override fun onBindViewHolder(holder: FavouriteRVHolder, position: Int) {
		holder.bind(games[position], position)
	}

	override fun getItemCount(): Int {
		return games.size
	}

	inner class FavouriteRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = ItemGamesVerticalBinding.bind(itemView)
		fun bind(game: Game, position: Int) {
			Glide.with(itemView.context)
				.load(game.background_image)
				.into(binding.ivSearchBanner)

			binding.tvSearchTitle.text = game.name

			itemView.setOnClickListener { onItemClick.invoke(game, position) }
		}
	}
}