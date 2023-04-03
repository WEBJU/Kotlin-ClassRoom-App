package com.example.mathed.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val scores = dbHelper.getScoresGroupedByStudentId()
        val studentScores = mutableListOf<StudentScore>()
        for (studentId in scores.keys) {
            val name = scores[studentId]?.first()?.first ?: ""
            val score = scores[studentId]?.map { it.second }?.sum() ?: 0
            val studentScore = StudentScore(studentId, name, score)
            Log.d("student", name + score + studentScore)
            studentScores.add(studentScore)
        }
        val adapter = ListScoresAdapter(studentScores)
        recyclerView.adapter = adapter
    }
}