package com.example.mathed.student

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mathed.R
import com.example.mathed.authentication.LoginStudentActivity
import com.example.mathed.parent.DisplayAllActivity
import com.example.mathed.parent.NewStudentActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val logoutButton = findViewById<Button>(R.id.logout)
        val viewScores = findViewById<Button>(R.id.view_score)
        val startTest = findViewById<Button>(R.id.start_test)

        viewScores.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        startTest.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            // Clear SharedPreferences
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // Go to login activity
            val intent = Intent(this, LoginStudentActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}