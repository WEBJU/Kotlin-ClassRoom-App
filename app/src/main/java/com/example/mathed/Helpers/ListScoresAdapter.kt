package com.example.mathed.Helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.R
import com.example.mathed.data.StudentScore

class ListScoresAdapter(private val scores: List<StudentScore>) : RecyclerView.Adapter<ListScoresAdapter.ViewHolder>() {
    // Inner class to define the view holder
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentNameTextView: TextView = view.findViewById(R.id.student_name_textview)
        val scoreTextView: TextView = view.findViewById(R.id.score_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val score = scores[position]
        holder.studentNameTextView.text = score.name
        holder.scoreTextView.text = score.score.toString()
    }

    override fun getItemCount(): Int {
        return scores.size
    }
}
