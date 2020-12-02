package com.emersonfiwre.testesofie.service.listener

import com.emersonfiwre.testesofie.service.model.TaskModel

interface TaskListener {

    /**
     * Click para atualizacao
     */
    fun onItemClick(task: TaskModel)

    /**
     * Remoção
     */
    fun onDeleteClick(id: String)
}