package com.example.mathed.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mathed.MainActivity
import com.example.mathed.R
import com.example.mathed.parent.AllStudentScoreActivity
import com.example.mathed.parent.NewStudentActivity
import com.example.mathed.student.DashboardActivity
import com.example.mathed.student.TestActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIMEOUT = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val username = sharedPrefs.getString("username", null)
            val role = sharedPrefs.getString("role", null)

            val intent = if (username == null) {
                Intent(this, LoginStudentActivity::class.java)
            } else {
                if (role == "parent") {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, DashboardActivity::class.java)
                }
            }
            startActivity(intent)
            finish()
        }, SPLASH_TIMEOUT)
    }
}