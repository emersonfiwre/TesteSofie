package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TaskModel() : Serializable {

    @SerializedName("_id")
    var id: String = ""

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("when")
    var whenDate: String = ""

    @SerializedName("title")
    lateinit var title: String

}