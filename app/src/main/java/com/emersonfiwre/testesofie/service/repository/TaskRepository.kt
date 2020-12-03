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

class TaskRepository(val mContext: Context) : ValidationRepository() {
    companion object {
        private const val TAG = "TaskRepositoryError"
    }

    private val mRemote = RetrofitClient.createService(TaskService::class.java)

    fun listTasks(listener: APIListener<List<TaskModel>>) {
        if (!isConnectionAvailable(mContext)) {
            listener.onFailure(mContext.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call: Call<TaskListModel> = mRemote.all()
        call.enqueue(object : Callback<TaskListModel> {
            override fun onResponse(call: Call<TaskListModel>, response: Response<TaskListModel>) {
                val code = response.code()
                if (fail(code)) {
                    if (isAuth(code)) {
                        listener.onFailure(mContext.getString(R.string.MISSING_AUTHENTICATION))
                    }
                    Log.e(TAG, failRespose(response.errorBody()!!.string()))
                    listener.onFailure(mContext.getString(R.string.ERROR_LOAD_TASK))
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

    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        if (!isConnectionAvailable(mContext)) {
            listener.onFailure(mContext.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call: Call<TaskCreateModel> = mRemote.create(task)
        call.enqueue(object : Callback<TaskCreateModel> {
            override fun onResponse(
                call: Call<TaskCreateModel>,
                response: Response<TaskCreateModel>
            ) {
                val code = response.code()
                if (code != TaskConstants.HTTP.CREATED) {
                    if (isAuth(code)) {
                        listener.onFailure(mContext.getString(R.string.MISSING_AUTHENTICATION))
                    }
                    Log.e(TAG, failRespose(response.errorBody()!!.string()))
                    listener.onFailure(mContext.getString(R.string.ERROR_CREATED))
                }
                response.body()?.let {
                    if (it.success) {
                        listener.onSuccess(it.success)
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

    fun update(task: TaskModel, listener: APIListener<Boolean>) {
        // Verificação de internet
        if (!isConnectionAvailable(mContext)) {
            listener.onFailure(mContext.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call: Call<TaskCreateModel> = mRemote.update(task.id, task)
        call.enqueue(object : Callback<TaskCreateModel> {
            override fun onResponse(
                call: Call<TaskCreateModel>,
                response: Response<TaskCreateModel>
            ) {
                val code = response.code()
                if (fail(code)) {
                    //Verificando se o usário está autenticado
                    if (isAuth(code)) {
                        listener.onFailure(mContext.getString(R.string.MISSING_AUTHENTICATION))
                    }
                    Log.e(TAG, failRespose(response.errorBody()!!.string()))
                    listener.onFailure(mContext.getString(R.string.ERROR_UPDATE))
                } else {
                    response.body()?.let { listener.onSuccess(it.success) }
                }
            }

            override fun onFailure(call: Call<TaskCreateModel>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED))
            }
        })

    }

    fun delete(id: String, listener: APIListener<Boolean>) {
        // Verificação de internet
        if (!isConnectionAvailable(mContext)) {
            listener.onFailure(mContext.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call: Call<TaskCreateModel> = mRemote.delete(id)
        call.enqueue(object : Callback<TaskCreateModel> {
            override fun onResponse(
                call: Call<TaskCreateModel>,
                response: Response<TaskCreateModel>
            ) {
                val code = response.code()
                if (fail(code)) {
                    //Verificando se o usário está autenticado
                    if (isAuth(code)) {
                        listener.onFailure(mContext.getString(R.string.MISSING_AUTHENTICATION))
                    }
                    Log.e(TAG, failRespose(response.errorBody()!!.string()))
                    listener.onFailure(mContext.getString(R.string.ERROR_DELETE))
                } else {
                    response.body()?.let { listener.onSuccess(it.success) }
                }
            }

            override fun onFailure(call: Call<TaskCreateModel>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }
}