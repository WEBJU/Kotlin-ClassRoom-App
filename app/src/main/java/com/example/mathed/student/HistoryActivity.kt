package com.example.mathed.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.Helpers.MyDatabaseHelper
import com.example.mathed.Helpers.ScoreAdapter
import com.example.mathed.R
import com.example.mathed.data.TTest

class HistoryActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var dbHelper: MyDatabaseHelper
    private lateinit var newArrayList: ArrayList<TTest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        dbHelper = MyDatabaseHelper(this)
        newRecyclerView = findViewById(R.id.recycler_view)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList= dbHelper.getMyScores()

        if (newArrayList.isEmpty()) {
            // Show a message indicating that there are no students
            val emptyText = findViewById<TextView>(R.id.empty)
            emptyText.visibility = View.VISIBLE
            newRecyclerView.visibility = View.GONE
        } else {
            // Set up the adapter and show the RecyclerView
            val adapter = ScoreAdapter(newArrayList)
            val emptyText = findViewById<TextView>(R.id.empty)
            newRecyclerView.adapter = adapter
            emptyText.visibility = View.GONE
            newRecyclerView.visibility = View.VISIBLE
        }
    }
}