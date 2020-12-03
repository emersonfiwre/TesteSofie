package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TaskCreateModel {
    @SerializedName("success")
    @Expose(serialize = true, deserialize = true)
    var success: Boolean = false

    @SerializedName("data")
    @Expose(serialize = true, deserialize = true)
    lateinit var task: TaskModel
}