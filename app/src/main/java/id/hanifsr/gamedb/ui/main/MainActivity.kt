package id.hanifsr.gamedb.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.hanifsr.gamedb.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val navController = findNavController(R.id.nav_host_fragment)
		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.navigation_home, R.id.navigation_search, R.id.navigation_favourites
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		nav_view.setupWithNavController(navController)

		supportActionBar?.elevation = 0F
	}
}