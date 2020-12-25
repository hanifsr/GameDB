package id.hanifsr.gamedb.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.source.local.GameHelper
import id.hanifsr.gamedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private var gameHelper: GameHelper? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		val navController = findNavController(R.id.nav_host_fragment)
		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.navigation_home, R.id.navigation_search, R.id.navigation_favourites
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		binding.navView.setupWithNavController(navController)

		supportActionBar?.elevation = 0F

		gameHelper = GameHelper.getInstance(applicationContext)
		gameHelper?.open()
	}

	override fun onDestroy() {
		super.onDestroy()
		gameHelper?.close()
	}
}