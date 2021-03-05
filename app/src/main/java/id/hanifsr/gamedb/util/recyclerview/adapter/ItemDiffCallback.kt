package id.hanifsr.gamedb.util.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

class ItemDiffCallback : DiffUtil.ItemCallback<GameEntity>() {

	override fun areItemsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
		return oldItem == newItem
	}
}