package id.hanifsr.gamedb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.ItemGamesVerticalBinding

class SearchRVAdapter(
	private var gameEntities: List<GameEntity>,
	private val onItemClick: (gameEntity: GameEntity) -> Unit
) : RecyclerView.Adapter<SearchRVAdapter.SearchRVHolder>() {

	fun updateSearch(gameEntities: List<GameEntity>) {
		this.gameEntities = gameEntities
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRVHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_games_vertical, parent, false)
		return SearchRVHolder(view)
	}

	override fun onBindViewHolder(holder: SearchRVHolder, position: Int) {
		holder.bind(gameEntities[position])
	}

	override fun getItemCount(): Int {
		return gameEntities.size
	}

	inner class SearchRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = ItemGamesVerticalBinding.bind(itemView)
		fun bind(gameEntity: GameEntity) {
			Glide.with(itemView.context)
				.load(gameEntity.backgroundImage)
				.into(binding.ivSearchBanner)

			binding.tvSearchTitle.text = gameEntity.name

			itemView.setOnClickListener { onItemClick.invoke(gameEntity) }
		}
	}
}