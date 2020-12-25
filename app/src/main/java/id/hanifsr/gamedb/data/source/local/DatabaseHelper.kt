package id.hanifsr.gamedb.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.BACKGROUND_IMAGE
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.DESCRIPTION_RAW
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.GENRES
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.ID
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.NAME
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.PUBLISHERS
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.RELEASED
import id.hanifsr.gamedb.data.source.local.DatabaseContract.GameColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
	SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	companion object {
		private const val DATABASE_NAME = "gamedb"
		private const val DATABASE_VERSION = 1
		private const val SQL_CREATE_TABLE_GAME =
			"CREATE TABLE $TABLE_NAME" +
					" (${ID} INTEGER PRIMARY KEY," +
					" $NAME TEXT NOT NULL," +
					" $GENRES TEXT NOT NULL," +
					" $RELEASED TEXT NOT NULL," +
					" $BACKGROUND_IMAGE TEXT NOT NULL," +
					" $PUBLISHERS TEXT NOT NULL," +
					" $DESCRIPTION_RAW TEXT NOT NULL)"

	}

	override fun onCreate(p0: SQLiteDatabase?) {
		p0?.execSQL(SQL_CREATE_TABLE_GAME)
	}

	override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
		p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
		onCreate(p0)
	}
}