package com.emersonfiwre.testesofie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emersonfiwre.testesofie.service.listener.APIListener
import com.emersonfiwre.testesofie.service.listener.ValidationListener
import com.emersonfiwre.testesofie.service.model.TaskModel
import com.emersonfiwre.testesofie.service.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: TaskRepository = TaskRepository(application)

    private val mTaskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = mTaskList

    //private val mTaskSave = MutableLiveData<Boolean>()
    //val taskSave: LiveData<Boolean> = mTaskSave

    private val mValidation = MutableLiveData<ValidationListener>()
    val validation: LiveData<ValidationListener> = mValidation

    fun list() {
        mRepository.listTasks(object : APIListener<List<TaskModel>> {
            override fun onSuccess(result: List<TaskModel>) {
                mTaskList.value = result
            }

            override fun onFailure(message: String) {
                mTaskList.value = arrayListOf()
                mValidation.value = ValidationListener(message)
            }
        })
    }

    fun save(email: String?, taskName: String?, desc: String?) {
        //mValidation.value = ValidationListener(message)
        if (email == null || email.isEmpty()) {
            mValidation.value = ValidationListener("email está vazio")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mValidation.value = ValidationListener("Adicione um email válido")
            return
        }
        if (taskName == null || taskName.isEmpty()) {
            mValidation.value = ValidationListener("Nome da tarefa está vazio")
            return
        }
        if (desc == null || desc.isEmpty()) {
            mValidation.value = ValidationListener("Descrição está vazio")
            return
        }
        val task = TaskModel().apply {
            this.email = email
            this.description = desc
            this.title = taskName
        }

        mRepository.create(task, object : APIListener<Boolean> {
            override fun onSuccess(result: Boolean) {
                mValidation.value = ValidationListener()
            }

            override fun onFailure(message: String) {
                mValidation.value = ValidationListener(message)
            }
        })
    }
}