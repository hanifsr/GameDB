package id.hanifsr.gamedb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import kotlinx.android.synthetic.main.item_search.view.*

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
			.inflate(R.layout.item_search, parent, false)
		return SearchRVHolder(view)
	}

	override fun onBindViewHolder(holder: SearchRVHolder, position: Int) {
		holder.bind(games[position])
	}

	override fun getItemCount(): Int {
		return games.size
	}

	inner class SearchRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(game: Game) {
			with(itemView) {
				Glide.with(itemView.context)
					.load(game.background_image)
					.into(iv_search_banner)

				tv_search_title.text = game.name

				itemView.setOnClickListener { onItemClick.invoke(game) }
			}
		}
	}
}