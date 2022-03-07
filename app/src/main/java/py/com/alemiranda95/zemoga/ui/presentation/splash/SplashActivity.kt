package py.com.alemiranda95.zemoga.ui.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.ui.presentation.main.MainActivity

//Used this instead of the API to keep the Author name on the splash screen
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        MainScope().launch {
            delay(2000)
            Intent(this@SplashActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
                finish()
            }
        }
    }
}