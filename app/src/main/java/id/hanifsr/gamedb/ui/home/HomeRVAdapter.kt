package id.hanifsr.gamedb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.ItemGamesHorizontalBinding

class HomeRVAdapter(
	private var gameEntities: List<GameEntity>,
	private val onItemClick: (gameEntity: GameEntity) -> Unit
) : RecyclerView.Adapter<HomeRVAdapter.HomeRVHolder>() {

	fun updateGames(gameEntities: List<GameEntity>) {
		this.gameEntities = gameEntities
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRVHolder {
		val view =
			LayoutInflater
				.from(parent.context)
				.inflate(R.layout.item_games_horizontal, parent, false)
		return HomeRVHolder(view)
	}

	override fun onBindViewHolder(holder: HomeRVHolder, position: Int) {
		holder.bind(gameEntities[position], position)
	}

	override fun getItemCount(): Int {
		return gameEntities.size
	}

	inner class HomeRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private var binding = ItemGamesHorizontalBinding.bind(itemView)
		fun bind(gameEntity: GameEntity, position: Int) {
			Glide.with(itemView.context)
				.load(gameEntity.backgroundImage)
				.into(binding.ivPoster)

			binding.tvItemNumber.text = (position + 1).toString()

			itemView.setOnClickListener { onItemClick.invoke(gameEntity) }
		}
	}
}