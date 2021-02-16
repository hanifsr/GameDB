package id.hanifsr.gamedb.di

import android.content.Context
import id.hanifsr.gamedb.data.source.GDBRepository
import id.hanifsr.gamedb.data.source.local.LocalDataSource
import id.hanifsr.gamedb.data.source.local.room.GDBDatabase

object Injection {

	fun provideRepository(context: Context): GDBRepository {
		val database = GDBDatabase.getInstance(context)
		val localDataSource = LocalDataSource.getInstance(database.gdbDao())

		return GDBRepository.getInstance(localDataSource)
	}
}