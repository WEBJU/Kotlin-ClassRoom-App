package com.example.mathed.Helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.R
import com.example.mathed.data.StudentScore
import com.example.mathed.data.TTest

class ScoreAdapter(private val studentScores: ArrayList<TTest>):
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_score_table,parent,false)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val currentItem = studentScores[position]
        holder.scoreText.text = currentItem.score.toString()
        holder.dateText.text = currentItem.date.toString()
    }

    override fun getItemCount(): Int {

        return studentScores.count()
    }
    class ScoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val scoreText : TextView =itemView.findViewById(R.id.score)
        val dateText : TextView =itemView.findViewById(R.id.date)
    }


}