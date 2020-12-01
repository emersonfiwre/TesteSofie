package com.emersonfiwre.testesofie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emersonfiwre.testesofie.service.listener.APIListener
import com.emersonfiwre.testesofie.service.model.TaskModel

class TaskViewModel : ViewModel() {
    //private val mTaskRepository: TaskRepository = TaskRepository(application)

    private val mTaskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = mTaskList

    fun list() {
        val listener = object : APIListener<List<TaskModel>> {
            override fun onSuccess(result: List<TaskModel>, statusCode: Int) {
                mTaskList.value = result
            }

            override fun onFailure(message: String) {
                mTaskList.value = null
                //mValidation.value = ValidationListener(message)
            }
        }

    }
}