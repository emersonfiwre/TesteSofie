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
import java.text.SimpleDateFormat
import java.util.*

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
                val local = Locale("pt", "BR")
                mTaskList.value = result.sortedByDescending {
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", local).parse(it.whenDate)
                }
            }

            override fun onFailure(message: String) {
                mTaskList.value = arrayListOf()
                mValidation.value = ValidationListener(message)
            }
        })
    }

    fun save(task: TaskModel) {
        //mValidation.value = ValidationListener(message)
        if (task.email.isEmpty()) {
            mValidation.value = ValidationListener("email está vazio")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(task.email).matches()) {
            mValidation.value = ValidationListener("Adicione um email válido")
            return
        }
        if (task.title.isEmpty()) {
            mValidation.value = ValidationListener("Nome da tarefa está vazio")
            return
        }
        if (task.description.isEmpty()) {
            mValidation.value = ValidationListener("Descrição está vazio")
            return
        }
        if (task.id.isEmpty()) {

            mRepository.create(task, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    mValidation.value = ValidationListener()
                    //list()
                }

                override fun onFailure(message: String) {
                    mValidation.value = ValidationListener(message)
                }
            })
        } else {
            mRepository.update(task, object : APIListener<Boolean> {
                override fun onSuccess(result: Boolean) {
                    mValidation.value = ValidationListener()
                }

                override fun onFailure(message: String) {
                    mValidation.value = ValidationListener(message)
                }

            })

        }
    }

    fun delete(id: String) {
        mRepository.delete(id, object : APIListener<Boolean> {
            override fun onSuccess(result: Boolean) {
                mValidation.value = ValidationListener()
                //list()
            }

            override fun onFailure(message: String) {
                mValidation.value = ValidationListener(message)
            }

        })
    }
}