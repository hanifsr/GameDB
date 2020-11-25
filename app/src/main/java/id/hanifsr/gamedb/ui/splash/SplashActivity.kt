package id.hanifsr.gamedb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
		window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.white)

		Handler(Looper.getMainLooper()).postDelayed({
			startActivity(Intent(this@SplashActivity, MainActivity::class.java))
			finish()
		}, 2000)
	}
}