package com.emersonfiwre.testesofie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emersonfiwre.testesofie.service.listener.APIListener
import com.emersonfiwre.testesofie.service.model.TaskModel
import com.emersonfiwre.testesofie.service.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: TaskRepository = TaskRepository(application)

    private val mTaskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = mTaskList

    fun list() {
        mRepository.listTasks(object : APIListener<List<TaskModel>> {
            override fun onSuccess(result: List<TaskModel>) {
                mTaskList.value = result
            }

            override fun onFailure(message: String) {
                val s =""
                mTaskList.value = arrayListOf()

                //mValidation.value = ValidationListener(message)
            }
        })

    }
}