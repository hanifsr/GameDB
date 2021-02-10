package id.hanifsr.gamedb.ui.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.ItemGamesVerticalBinding

class FavouritesRVAdapter(
	private var gameEntities: MutableList<GameEntity>,
	private val onItemClick: (gameEntity: GameEntity, position: Int) -> Unit
) : RecyclerView.Adapter<FavouritesRVAdapter.FavouriteRVHolder>() {

	fun updateFavouriteRVData(gameEntities: List<GameEntity>) {
		this.gameEntities = gameEntities.toMutableList()
		notifyDataSetChanged()
	}

	fun removeItem(position: Int) {
		gameEntities.removeAt(position)
		notifyItemRemoved(position)
		notifyItemRangeChanged(position, gameEntities.size)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRVHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_games_vertical, parent, false)
		return FavouriteRVHolder(view)
	}

	override fun onBindViewHolder(holder: FavouriteRVHolder, position: Int) {
		holder.bind(gameEntities[position], position)
	}

	override fun getItemCount(): Int {
		return gameEntities.size
	}

	inner class FavouriteRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = ItemGamesVerticalBinding.bind(itemView)
		fun bind(gameEntity: GameEntity, position: Int) {
			Glide.with(itemView.context)
				.load(gameEntity.backgroundImage)
				.into(binding.ivSearchBanner)

			binding.tvSearchTitle.text = gameEntity.name

			itemView.setOnClickListener { onItemClick.invoke(gameEntity, position) }
		}
	}
}