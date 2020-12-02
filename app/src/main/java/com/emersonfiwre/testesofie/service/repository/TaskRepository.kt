package com.emersonfiwre.testesofie.service.repository

import android.content.Context
import android.util.Log
import com.emersonfiwre.testesofie.R
import com.emersonfiwre.testesofie.service.constants.TaskConstants
import com.emersonfiwre.testesofie.service.listener.APIListener
import com.emersonfiwre.testesofie.service.model.TaskListModel
import com.emersonfiwre.testesofie.service.model.TaskModel
import com.emersonfiwre.testesofie.service.model.TaskCreateModel
import com.emersonfiwre.testesofie.service.repository.remote.RetrofitClient
import com.emersonfiwre.testesofie.service.repository.remote.TaskService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val mContext: Context) {
    companion object {
        private const val TAG = "TaskRepositoryError"
    }

    private val mRemote = RetrofitClient.createService(TaskService::class.java)

    fun listTasks(listener: APIListener<List<TaskModel>>) {
        val call: Call<TaskListModel> = mRemote.all()
        call.enqueue(object : Callback<TaskListModel> {
            override fun onResponse(call: Call<TaskListModel>, response: Response<TaskListModel>) {
                val code = response.code()
                if (code != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                }
                response.body()?.let { listener.onSuccess(it.tasks) }
            }

            override fun onFailure(call: Call<TaskListModel>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, t.message.toString())
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }

    fun create(task: TaskModel, listener: APIListener<TaskModel>) {
        val call: Call<TaskCreateModel> = mRemote.create(
            task.title,
            task.description,
            task.email
        )
        call.enqueue(object : Callback<TaskCreateModel> {
            override fun onResponse(call: Call<TaskCreateModel>, response: Response<TaskCreateModel>) {
                val code = response.code()
                if (code != TaskConstants.HTTP.CREATED) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    Log.e(TAG, validation)
                    listener.onFailure(mContext.getString(R.string.ERROR_CREATED))
                }
                response.body()?.let {
                    if (it.success) {
                        listener.onSuccess(it.task)
                    } else {
                        listener.onFailure(mContext.getString(R.string.ERROR_CREATED))
                    }
                }

            }

            override fun onFailure(call: Call<TaskCreateModel>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, t.message.toString())
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }
}