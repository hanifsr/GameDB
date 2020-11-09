package id.hanifsr.gamedb.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.util.OnItemClickCallback
import kotlinx.android.synthetic.main.item_games.view.*

class GameRVAdapter(private val listGame: ArrayList<Game>) :
	RecyclerView.Adapter<GameRVAdapter.GameRVHolder>() {

	private lateinit var onItemClickCallback: OnItemClickCallback

	fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
		this.onItemClickCallback = onItemClickCallback
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameRVHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.item_games, parent, false)
		return GameRVHolder(view)
	}

	override fun onBindViewHolder(holder: GameRVHolder, position: Int) {
		holder.bind(listGame[position])
	}

	override fun getItemCount(): Int {
		return listGame.size
	}

	inner class GameRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(game: Game) {
			with(itemView) {
				Glide.with(itemView.context)
					.load(game.poster)
					.into(iv_poster)

				itemView.setOnClickListener { onItemClickCallback.onItemClicked(game) }
			}
		}
	}
}