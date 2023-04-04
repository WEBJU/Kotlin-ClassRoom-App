package com.example.mathed.Helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.R
import com.example.mathed.data.TStudent
import com.example.mathed.data.TTest

class AllScoresAdapter(private val studentScore: ArrayList<TTest>):
    RecyclerView.Adapter<AllScoresAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.all_student_score,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentItem = studentScore[position]
        holder.nameText.text =currentItem.score.toString()

    }

    override fun getItemCount(): Int {

        return studentScore.count()
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameText :TextView=itemView.findViewById(R.id.student_name)

    }

}