package id.hanifsr.gamedb.util

import java.text.SimpleDateFormat
import java.util.*

object Util {

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

	fun <T> isListEqual(firstList: List<T>, secondList: List<T>): Boolean {
		if (firstList.size != secondList.size) {
			return false
		}

		return firstList.zip(secondList).all { (first, second) -> first == second }
	}
}