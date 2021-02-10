package id.hanifsr.gamedb.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class ItemDecoration(
	private val screenWidth: Int
) : RecyclerView.ItemDecoration() {

	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		val itemCount = parent.adapter?.itemCount

		//first element
		if (parent.getChildAdapterPosition(view) == 0) {
			var firstPadding = screenWidth / 6
			firstPadding = max(0, firstPadding)
			outRect.set(firstPadding, 0, 0, 0)
		}

		//last element
		if (parent.getChildAdapterPosition(view) == itemCount?.minus(1) && itemCount > 1) {
			var lastPadding = screenWidth / 6
			lastPadding = max(0, lastPadding)
			outRect.set(0, 0, lastPadding, 0)
		}
	}
}