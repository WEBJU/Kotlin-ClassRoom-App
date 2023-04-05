package com.example.mathed.Helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.R
import com.example.mathed.data.StudentScore
import java.text.SimpleDateFormat
import java.util.*

class ScoreDateAdapter(private val scores: List<Pair<Int,Long>>) : RecyclerView.Adapter<ScoreDateAdapter.ViewHolder>() {
    // Inner class to define the view holder
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentNameTextView: TextView = view.findViewById(R.id.student_name_textview)
        val scoreTextView: TextView = view.findViewById(R.id.score_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_score_date, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val score = scores[position]
        holder.studentNameTextView.text = score.first.toString()
        val date = Date(score.second)
        val scoreDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        holder.scoreTextView.text = scoreDateFormat.format(date).toString()
    }

    override fun getItemCount(): Int {
        return scores.size
    }
}
