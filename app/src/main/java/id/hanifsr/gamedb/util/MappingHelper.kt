package id.hanifsr.gamedb.util

import android.database.Cursor
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.data.model.Genre
import id.hanifsr.gamedb.data.model.Publisher
import id.hanifsr.gamedb.data.source.local.DatabaseContract

object MappingHelper {

	fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Game> {
		val games = ArrayList<Game>()
		var i = -1

		cursor?.apply {
			while (moveToNext()) {
				i++
				val id = getInt(getColumnIndexOrThrow(DatabaseContract.GameColumns.ID))
				val name = getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.NAME))
				val genres = getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.GENRES))
				val released =
					getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.RELEASED))
				val backgroundImage =
					getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.BACKGROUND_IMAGE))
				val publishers =
					getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.PUBLISHERS))
				val descriptionRaw =
					getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.DESCRIPTION_RAW))

				games.add(
					Game(
						id,
						name,
						genres.split(", ").map { Genre(i, it, it) },
						released,
						backgroundImage,
						publishers.split(", ").map { Publisher(i, it) },
						descriptionRaw
					)
				)
			}
		}

		cursor?.close()

		return games
	}
}