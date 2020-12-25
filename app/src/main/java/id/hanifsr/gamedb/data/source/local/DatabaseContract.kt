package id.hanifsr.gamedb.data.source.local

import android.provider.BaseColumns

internal class DatabaseContract {

	internal class GameColumns : BaseColumns {
		companion object {
			const val TABLE_NAME = "game"
			const val ID = "id"
			const val NAME = "name"
			const val GENRES = "genres"
			const val RELEASED = "released"
			const val BACKGROUND_IMAGE = "background_image"
			const val PUBLISHERS = "publishers"
			const val DESCRIPTION_RAW = "description_raw"
		}
	}
}