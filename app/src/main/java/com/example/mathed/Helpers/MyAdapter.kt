package com.example.mathed.Helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathed.R
import com.example.mathed.data.TStudent

class MyAdapter(private val studentList : ArrayList<TStudent>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.all_student_score,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentItem = studentList[position]
        holder.nameText.text =currentItem.userName
        holder.average.text = currentItem.userPassWord
    }

    override fun getItemCount(): Int {

        return studentList.count()
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameText :TextView=itemView.findViewById(R.id.student_name)
        val average :TextView=itemView.findViewById(R.id.average)
    }

}