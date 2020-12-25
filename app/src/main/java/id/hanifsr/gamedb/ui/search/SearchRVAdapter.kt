package id.hanifsr.gamedb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.ItemGamesVerticalBinding

class SearchRVAdapter(
	private var games: List<Game>,
	private val onItemClick: (game: Game) -> Unit
) : RecyclerView.Adapter<SearchRVAdapter.SearchRVHolder>() {

	fun updateSearch(games: List<Game>) {
		this.games = games
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRVHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_games_vertical, parent, false)
		return SearchRVHolder(view)
	}

	override fun onBindViewHolder(holder: SearchRVHolder, position: Int) {
		holder.bind(games[position])
	}

	override fun getItemCount(): Int {
		return games.size
	}

	inner class SearchRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = ItemGamesVerticalBinding.bind(itemView)
		fun bind(game: Game) {
			Glide.with(itemView.context)
				.load(game.background_image)
				.into(binding.ivSearchBanner)

			binding.tvSearchTitle.text = game.name

			itemView.setOnClickListener { onItemClick.invoke(game) }
		}
	}
}