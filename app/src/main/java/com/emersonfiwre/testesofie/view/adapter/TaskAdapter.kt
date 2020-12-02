package com.emersonfiwre.testesofie.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.emersonfiwre.testesofie.R
import com.emersonfiwre.testesofie.service.listener.TaskListener
import com.emersonfiwre.testesofie.service.model.TaskModel
import kotlinx.android.synthetic.main.card_task.view.*

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var mList: List<TaskModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.card_task, parent, false)
        return TaskViewHolder(item)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(mList[position])
    }

    fun notifyChanged(list: List<TaskModel>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var mCardTask: ConstraintLayout = itemView.card_task
        private var mTitle: TextView = itemView.txt_title
        private var mEmail: TextView = itemView.txt_email

        fun bindTask(task: TaskModel) {
            mTitle.text = task.title
            mEmail.text = task.email
        }

    }
}