package id.hanifsr.gamedb.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.databinding.ItemGamesVerticalBinding
import id.hanifsr.gamedb.domain.Game

class FavouritesRVAdapter(
	private val onItemClick: (game: Game, position: Int) -> Unit
) : RecyclerView.Adapter<FavouritesRVAdapter.FavouriteRVHolder>() {

	var games: List<Game> = emptyList()

	fun updateFavouriteRVData(games: List<Game>) {
		this.games = games
		notifyDataSetChanged()
	}

	fun removeItem(position: Int) {
		val mutableGameEntities = games.toMutableList()
		mutableGameEntities.removeAt(position)
		games = mutableGameEntities
		notifyItemRemoved(position)
		notifyItemRangeChanged(position, games.size)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRVHolder {
		return FavouriteRVHolder.from(parent)
	}

	override fun onBindViewHolder(holder: FavouriteRVHolder, position: Int) {
		holder.bind(games[position], position, onItemClick)
	}

	override fun getItemCount(): Int {
		return games.size
	}

	class FavouriteRVHolder private constructor(private val binding: ItemGamesVerticalBinding) :
		RecyclerView.ViewHolder(binding.root) {

		companion object {
			fun from(parent: ViewGroup): FavouriteRVHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = ItemGamesVerticalBinding.inflate(layoutInflater, parent, false)

				return FavouriteRVHolder(binding)
			}
		}

		fun bind(
			game: Game,
			position: Int,
			onItemClick: (game: Game, position: Int) -> Unit
		) {
			binding.game = game
			itemView.setOnClickListener {
				onItemClick.invoke(
					game,
					position
				)
			}
		}
	}
}