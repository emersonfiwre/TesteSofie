package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.SerializedName
import java.util.*

class TaskModel {

    @SerializedName("_id")
    var id: String = ""

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("when")
    lateinit var whenDate: String

    @SerializedName("title")
    lateinit var title: String

}