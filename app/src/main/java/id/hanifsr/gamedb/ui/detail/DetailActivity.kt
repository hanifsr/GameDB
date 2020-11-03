package id.hanifsr.gamedb.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import id.hanifsr.gamedb.R

class DetailActivity : AppCompatActivity() {

	companion object {
		const val EXTRA_TITLE = "extra_title"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)

		val tvDetailTitle: TextView = findViewById(R.id.tv_detail_title)

		val title = intent.getStringExtra(EXTRA_TITLE)

		tvDetailTitle.text = title
	}
}