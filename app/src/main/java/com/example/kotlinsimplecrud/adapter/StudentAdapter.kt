package com.example.kotlinsimplecrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsimplecrud.R
import com.example.kotlinsimplecrud.model.Student

class StudentAdapter(
    private val listItems: ArrayList<Student>,
    private val listener: StudentListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val item = listItems[position]
        holder.textViewStudent.text = item.name
        holder.textViewNim.text = item.nim
        holder.textViewQuote.text = item.quote
        holder.itemView.setOnClickListener {
            listener.OnItemClicked(item)
        }
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewStudent = itemView.findViewById<TextView>(R.id.text_view_title)
        var textViewNim = itemView.findViewById<TextView>(R.id.tv_nim)
        var textViewQuote = itemView.findViewById<TextView>(R.id.text_view_body)
    }

    interface StudentListener{
        fun OnItemClicked(student: Student)
    }
}