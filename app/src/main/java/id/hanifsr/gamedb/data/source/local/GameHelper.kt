package id.hanifsr.gamedb.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.ID
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.NAME
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.TABLE_NAME

class GameHelper(context: Context) {

	private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
	private lateinit var database: SQLiteDatabase

	companion object {
		private const val DATABASE_TABLE = TABLE_NAME
		private var INSTANCE: GameHelper? = null

		fun getInstance(context: Context): GameHelper? =
			INSTANCE ?: synchronized(this) {
				if (INSTANCE == null) {
					INSTANCE = GameHelper(context)
				}
				INSTANCE
			}

		fun getInstance(): GameHelper? = INSTANCE
	}

	@Throws(SQLException::class)
	fun open() {
		database = databaseHelper.writableDatabase
	}

	fun close() {
		databaseHelper.close()

		if (database.isOpen) {
			database.close()
		}
	}

	fun queryAll(): Cursor {
		return database.query(
			DATABASE_TABLE,
			null,
			null,
			null,
			null,
			null,
			"$NAME ASC"
		)
	}

	fun queryById(id: Int): Cursor {
		return database.query(
			DATABASE_TABLE,
			null,
			"$ID = ?",
			arrayOf(id.toString()),
			null,
			null,
			null,
			null
		)
	}

	fun insert(values: ContentValues?): Long {
		return database.insert(
			DATABASE_TABLE, null, values
		)
	}

	fun deleteById(id: Int): Int {
		return database.delete(
			DATABASE_TABLE, "$ID = ?", arrayOf(id.toString())
		)
	}
}