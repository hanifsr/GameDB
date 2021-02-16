package id.hanifsr.gamedb.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
	@PrimaryKey
	@ColumnInfo
	val id: Int,

	@ColumnInfo val name: String,
	@ColumnInfo val genres: String,
	@ColumnInfo val released: String,
	@ColumnInfo val rating: Double,
	@ColumnInfo val ratingTop: Double,
	@ColumnInfo val developers: String,
	@ColumnInfo val backgroundImage: String,
	@ColumnInfo val description: String,
	@ColumnInfo var isFavourite: Boolean
)
