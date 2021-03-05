package id.hanifsr.gamedb.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.databinding.ItemGamesVerticalBinding
import id.hanifsr.gamedb.domain.Game

class SearchRVAdapter(
	private val onItemClick: (game: Game) -> Unit
) : RecyclerView.Adapter<SearchRVAdapter.SearchRVHolder>() {

	var games: List<Game> = emptyList()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRVHolder {
		return SearchRVHolder.from(parent)
	}

	override fun onBindViewHolder(holder: SearchRVHolder, position: Int) {
		holder.bind(games[position], onItemClick)
	}

	override fun getItemCount(): Int {
		return games.size
	}

	class SearchRVHolder private constructor(private val binding: ItemGamesVerticalBinding) :
		RecyclerView.ViewHolder(binding.root) {

		companion object {
			fun from(parent: ViewGroup): SearchRVHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = ItemGamesVerticalBinding.inflate(layoutInflater, parent, false)

				return SearchRVHolder(binding)
			}
		}

		fun bind(game: Game, onItemClick: (game: Game) -> Unit) {
			binding.game = game
			itemView.setOnClickListener { onItemClick.invoke(game) }
		}
	}
}