package com.cikup.todolist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.cikup.todolist.R
import com.cikup.todolist.model.TaskModel
import com.cikup.todolist.ui.task.DetailTaskActivity
import kotlinx.android.synthetic.main.item_task.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterTask(private val taskModel: ArrayList<TaskModel>): RecyclerView.Adapter<AdapterTask.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val myFormat = "MMM, dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)

        val data = taskModel[position]
        val view = holder.itemView

        view.timeTaskItemViewTV.text = sdf.format(data.dateTime.toDate())
        view.titleTaskTV.text = data.title
        view.descriptionTV.text = data.description
        if (data.status){
            view.statusTV.text = "Complete"
        }else{
            view.statusTV.text = "Not Finished"
        }

        view.setOnClickListener {
            val intent = Intent(context, DetailTaskActivity::class.java)
            intent.putExtra("id", data.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = taskModel.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}