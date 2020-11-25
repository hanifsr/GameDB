package id.hanifsr.gamedb.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import kotlinx.android.synthetic.main.item_games.view.*

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
				.inflate(R.layout.item_games, parent, false)
		return GameRVHolder(view)
	}

	override fun onBindViewHolder(holder: GameRVHolder, position: Int) {
		holder.bind(games[position], position)
	}

	override fun getItemCount(): Int {
		return games.size
	}

	inner class GameRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(game: Game, position: Int) {
			with(itemView) {
				Glide.with(itemView.context)
					.load(game.background_image)
					.into(iv_poster)

				tv_item_number.text = (position + 1).toString()

				itemView.setOnClickListener { onItemClick.invoke(game) }
			}
		}
	}
}