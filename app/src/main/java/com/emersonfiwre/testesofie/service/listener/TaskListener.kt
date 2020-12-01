package com.emersonfiwre.testesofie.service.listener

import com.emersonfiwre.testesofie.service.model.TaskModel

interface TaskListener {

    /**
     * Click para inserção
     */
    fun onSaveClick(id: TaskModel)


}