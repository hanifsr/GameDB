package id.hanifsr.gamedb.util

import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.data.source.remote.response.GameDetailResponse
import id.hanifsr.gamedb.data.source.remote.response.GameListResponse
import java.text.SimpleDateFormat
import java.util.*

object MappingHelper {

	fun gameListResponseToGameEntitiesMapper(gameListResponse: GameListResponse?): List<GameEntity> {
		return gameListResponse?.results?.map {
			GameEntity(
				it?.id ?: 0,
				it?.name ?: "",
				it?.genres?.joinToString { genre -> genre?.name ?: "" } ?: "",
				"",
				it?.rating ?: 0.0,
				it?.ratingTop ?: 0.0,
				"",
				it?.backgroundImage ?: "",
				"",
				false

			)
		} ?: emptyList()
	}

	fun gameDetailResponseToGameEntityMapper(gameDetailResponse: GameDetailResponse?): GameEntity {
		return with(gameDetailResponse) {
			GameEntity(
				this?.id ?: 0,
				this?.name ?: "",
				this?.genres?.joinToString { genre -> genre?.name ?: "" } ?: "",
				this?.released ?: "",
				this?.rating ?: 0.0,
				this?.ratingTop ?: 0.0,
				this?.developers?.joinToString { developer -> developer?.name ?: "" } ?: "",
				this?.backgroundImage ?: "",
				this?.descriptionRaw ?: "",
				false
			)
		}
	}

	fun getFirstAndCurrentDate(): String {
		val firstDate = SimpleDateFormat("yyyy-MM-01", Locale.getDefault()).format(Date())
		val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

		return "$firstDate,$currentDate"
	}

	fun dateFormat(unformattedDate: String): String {
		return if (unformattedDate.isEmpty()) {
			"TBA"
		} else {
			val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(unformattedDate)
			if (date == null) {
				"TBA"
			} else {
				SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
			}
		}
	}

}