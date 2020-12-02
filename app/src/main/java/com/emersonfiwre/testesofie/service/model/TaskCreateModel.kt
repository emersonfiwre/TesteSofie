package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.SerializedName

class TaskCreateModel {
    @SerializedName("success")
    var success: Boolean = false

    @SerializedName("data")
    lateinit var task: TaskModel
}