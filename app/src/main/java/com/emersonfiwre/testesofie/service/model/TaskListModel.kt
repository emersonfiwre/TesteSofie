package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TaskListModel {
    @SerializedName("data")
    @Expose(serialize = true, deserialize = true)
    lateinit var tasks: List<TaskModel>
}