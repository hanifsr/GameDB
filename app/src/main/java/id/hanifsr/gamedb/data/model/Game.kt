package id.hanifsr.gamedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
	var title: String = "",
	var genre: String = "",
	var poster: Int = 0,
	var banner: Int = 0
) : Parcelable