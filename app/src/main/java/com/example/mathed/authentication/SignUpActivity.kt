package com.example.mathed.authentication

import android.content.ContentValues
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
import com.example.mathed.data.TParent

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPassword)

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginButton = findViewById<Button>(R.id.Login)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val dbHelper = MyDatabaseHelper(this)

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val exists = dbHelper.checkUserExists(username)
                if (exists) {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val created = dbHelper.createUser(username, password)
                    if (created) {
                        val sharedPreferences =
                            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
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
                        Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginStudentActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}