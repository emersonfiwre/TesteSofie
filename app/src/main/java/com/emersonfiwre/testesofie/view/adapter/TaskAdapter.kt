package com.emersonfiwre.testesofie.view.adapter

import android.app.AlertDialog
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
    private lateinit var mListener: TaskListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.card_task, parent, false)
        return TaskViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(mList[position])
    }

    fun attachListener(listener: TaskListener) {
        mListener = listener
    }

    fun notifyChanged(list: List<TaskModel>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View, val listener: TaskListener) :
        RecyclerView.ViewHolder(itemView) {
        private var mCardTask: ConstraintLayout = itemView.card_task
        private var mTitle: TextView = itemView.txt_title
        private var mEmail: TextView = itemView.txt_email

        fun bindTask(task: TaskModel) {
            mTitle.text = task.title
            mEmail.text = task.email

            mCardTask.setOnClickListener { listener.onItemClick(task) }

            mCardTask.setOnLongClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle(R.string.remocao_de_tarefa)
                    .setMessage(R.string.remover_tarefa)
                    .setPositiveButton(R.string.sim) { dialog, which ->
                        listener.onDeleteClick(task.id)
                    }
                    .setNeutralButton(R.string.cancelar, null)
                    .show()
                true
            }
        }

    }
}