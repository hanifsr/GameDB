package id.hanifsr.gamedb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.ItemGamesHorizontalBinding

class GameRVAdapter(
	private var games: List<Game>,
	private val onItemClick: (game: Game) -> Unit
) : RecyclerView.Adapter<GameRVAdapter.GameRVHolder>() {

	fun updateGames(games: List<Game>) {
		this.games = games
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameRVHolder {
		val view =
			LayoutInflater
				.from(parent.context)
				.inflate(R.layout.item_games_horizontal, parent, false)
		return GameRVHolder(view)
	}

	override fun onBindViewHolder(holder: GameRVHolder, position: Int) {
		holder.bind(games[position], position)
	}

	override fun getItemCount(): Int {
		return games.size
	}

	inner class GameRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private var binding = ItemGamesHorizontalBinding.bind(itemView)
		fun bind(game: Game, position: Int) {
			Glide.with(itemView.context)
				.load(game.background_image)
				.into(binding.ivPoster)

			binding.tvItemNumber.text = (position + 1).toString()

			itemView.setOnClickListener { onItemClick.invoke(game) }
		}
	}
}