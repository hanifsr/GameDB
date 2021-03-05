package id.hanifsr.gamedb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.databinding.ItemGamesHorizontalBinding
import id.hanifsr.gamedb.domain.Game

class HomeRVAdapter(
	private val onItemClick: (game: Game) -> Unit
) : RecyclerView.Adapter<HomeRVAdapter.HomeRVHolder>() {

	var games: List<Game> = emptyList()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRVHolder {
		return HomeRVHolder.from(parent)
	}

	override fun onBindViewHolder(holder: HomeRVHolder, position: Int) {
		holder.bind(games[position], position, onItemClick)
	}

	override fun getItemCount(): Int {
		return games.size
	}

	class HomeRVHolder private constructor(private val binding: ItemGamesHorizontalBinding) :
		RecyclerView.ViewHolder(binding.root) {

		companion object {
			fun from(parent: ViewGroup): HomeRVHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = ItemGamesHorizontalBinding.inflate(layoutInflater, parent, false)
				return HomeRVHolder(binding)
			}
		}

		fun bind(
			game: Game,
			position: Int,
			onItemClick: (game: Game) -> Unit
		) {
			binding.game = game
			binding.position = (position + 1).toString()
			itemView.setOnClickListener { onItemClick.invoke(game) }
		}
	}
}