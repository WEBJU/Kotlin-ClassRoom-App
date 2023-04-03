package com.example.mathed

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mathed.Helpers.MyDatabaseHelper
import com.example.mathed.authentication.LoginStudentActivity
import com.example.mathed.parent.AllStudentScoreActivity
import com.example.mathed.parent.DisplayAllActivity
import com.example.mathed.parent.NewStudentActivity
import com.example.mathed.parent.StudentScoreActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = MyDatabaseHelper(this)
        val answers = dbHelper.getAnswersForQuestion(1)
        val questionsAndAnswers = dbHelper.getQuestionsAndAnswers()
        val logoutButton = findViewById<Button>(R.id.logout)
        val createNew = findViewById<Button>(R.id.create_new)
        val displayAll = findViewById<Button>(R.id.display_all)
        val displayStudentScores = findViewById<Button>(R.id.student_scores)
        val displayAllScores = findViewById<Button>(R.id.all_scores)

        createNew.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }

        displayAll.setOnClickListener {
            val intent = Intent(this, DisplayAllActivity::class.java)
            startActivity(intent)
        }

        displayStudentScores.setOnClickListener {
            val intent = Intent(this, StudentScoreActivity::class.java)
            startActivity(intent)
        }

        displayAllScores.setOnClickListener {
            val intent = Intent(this, AllStudentScoreActivity::class.java)
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
        questionsAndAnswers.forEachIndexed { index, (question, answers) ->
            Log.d("Question ${index + 1}", question)
            answers.forEachIndexed { answerIndex, answer ->
                Log.d("Answer ${index + 1}-${answerIndex + 1}", "${answer}+${answer.isCorrect}")
            }
        }


    }
}