package com.emersonfiwre.testesofie.service.repository

import android.content.Context
import com.emersonfiwre.testesofie.R
import com.emersonfiwre.testesofie.service.listener.APIListener
import com.emersonfiwre.testesofie.service.model.TaskModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val mContext: Context) {

    private val mRemote = RetrofitClient.createService(TaskService::class.java)

    fun all(listener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.all()
        listTasks(call, listener)
    }

    private fun listTasks(call: Call<List<TaskModel>>, listener: APIListener<List<TaskModel>>) {
        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                val code = response.code()
                if (code != 200) {
                    listener.onFailure(response.errorBody()!!.string())
                } else {
                    response.body()?.let { listener.onSuccess(it, code) }
                }
            }
        })
    }

    fun create(task: TaskModel, listener: APIListener<TaskModel>) {
        val call: Call<TaskModel> = mRemote.create(
            task.title,
            task.description,
            task.email
        )
        call.enqueue(object : Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>, t: Throwable) {
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {
                val code = response.code()
                if (code != 200) {
                    listener.onFailure(response.errorBody()!!.string())
                } else {
                    response.body()?.let { listener.onSuccess(it, code) }
                }
            }
        })
    }
}