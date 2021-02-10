package id.hanifsr.gamedb.di

import android.content.Context
import id.hanifsr.gamedb.data.source.GDBRepository

object Injection {

	fun provideRepository(context: Context): GDBRepository {
		return GDBRepository.getInstance()
	}
}