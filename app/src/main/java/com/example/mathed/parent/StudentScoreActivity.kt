package com.example.mathed.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.Helpers.AllScoresAdapter
import com.example.mathed.Helpers.ListScoresAdapter
import com.example.mathed.Helpers.MyDatabaseHelper
import com.example.mathed.Helpers.ScoreAdapter
import com.example.mathed.R
import com.example.mathed.data.StudentScore

class StudentScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_score)
        val dbHelper = MyDatabaseHelper(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val scores = dbHelper.getScoresGroupedByStudentId()
        val studentScores = mutableListOf<StudentScore>()
        for (studentId in scores.keys) {
            val name = scores[studentId]?.first()?.first ?: ""
            val score = scores[studentId]?.map { Pair(it.second, it.third) } ?: emptyList()

            val studentScore = StudentScore(studentId, name, score)
            Log.d("student", name + score + studentScore)
            studentScores.add(studentScore)
        }
        if (studentScores.isEmpty()) {
            // Show a message indicating that there are no students
            val emptyText = findViewById<TextView>(R.id.empty)
            emptyText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Set up the adapter and show the RecyclerView

            val emptyText = findViewById<TextView>(R.id.empty)

            emptyText.visibility = View.GONE

            val adapter = ListScoresAdapter(studentScores)
            recyclerView.adapter = adapter
            recyclerView.visibility = View.VISIBLE
        }

    }
}