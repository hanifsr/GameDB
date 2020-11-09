package id.hanifsr.gamedb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		Handler(Looper.getMainLooper()).postDelayed({
			startActivity(Intent(this@SplashActivity, MainActivity::class.java))
			finish()
		}, 2000)
	}
}