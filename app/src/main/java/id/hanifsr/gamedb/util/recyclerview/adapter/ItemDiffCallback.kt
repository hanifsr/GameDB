package id.hanifsr.gamedb.util.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import id.hanifsr.gamedb.data.source.local.entity.FavouriteEntity

class ItemDiffCallback : DiffUtil.ItemCallback<FavouriteEntity>() {

	override fun areItemsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
		return oldItem == newItem
	}
}