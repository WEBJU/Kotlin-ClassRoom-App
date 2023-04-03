package com.example.mathed.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mathed.Helpers.MyDatabaseHelper
import com.example.mathed.MainActivity
import com.example.mathed.R
import com.example.mathed.student.DashboardActivity

class LoginStudentActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_student)
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)

        val loginButton = findViewById<Button>(R.id.Login)
        val signUpButton = findViewById<Button>(R.id.SignUp)
        val parentLoginButton = findViewById<Button>(R.id.parent_login)

        parentLoginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val dbHelper = MyDatabaseHelper(this)
            val authenticate = dbHelper.authenticateUser(username, password)

            if (authenticate) {
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("role", "parent")
                editor.apply()
                Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val dbHelper = MyDatabaseHelper(this)
            val authenticate = dbHelper.authenticateStudent(username, password)

            if (authenticate) {
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("role", "student")
                editor.apply()
                Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}