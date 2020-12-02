package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.SerializedName

class TaskListModel {
    @SerializedName("data")
    lateinit var tasks: List<TaskModel>
}