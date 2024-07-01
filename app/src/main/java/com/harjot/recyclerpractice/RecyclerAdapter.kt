package com.harjot.recyclerpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var array:ArrayList<UserModel>,var recyclerInterface: RecyclerInterface): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var lv = view.findViewById<LinearLayout>(R.id.lv)
        var name = view.findViewById<TextView>(R.id.tvName)
        var rollno = view.findViewById<TextView>(R.id.tvRollNo)
        var subject = view.findViewById<TextView>(R.id.tvSubject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item
            ,parent
            ,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
    return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(array[position].name)
        holder.rollno.setText(array[position].rollNo.toString())
        holder.subject.setText(array[position].subject)

        holder.lv.setOnClickListener {
            recyclerInterface.onListclicked(position)
        }
    }
}