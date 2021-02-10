package id.hanifsr.gamedb.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var activityMainBinding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(activityMainBinding.root)

		val navController = findNavController(R.id.nav_host_fragment)
		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.navigation_home, R.id.navigation_search, R.id.navigation_favourites
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		activityMainBinding.navView.setupWithNavController(navController)

		supportActionBar?.elevation = 0F
	}
}