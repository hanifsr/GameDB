package id.hanifsr.gamedb.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.hanifsr.gamedb.data.source.local.entity.GameEntity

@Database(
	entities = [GameEntity::class],
	version = 1,
	exportSchema = false
)
abstract class GDBDatabase : RoomDatabase() {

	abstract fun gdbDao(): GDBDao

	companion object {
		@Volatile
		private var INSTANCE: GDBDatabase? = null

		fun getInstance(context: Context): GDBDatabase =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?: Room.databaseBuilder(
					context.applicationContext,
					GDBDatabase::class.java,
					"gdb"
				).build()
			}
	}
}