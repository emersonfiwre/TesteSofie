package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class TaskModel() : Serializable {

    @SerializedName("_id")
    @Expose(serialize = false)
    var id: String = ""

    @SerializedName("description")
    @Expose(serialize = true, deserialize = true)
    lateinit var description: String

    @SerializedName("email")
    @Expose(serialize = true, deserialize = true)
    lateinit var email: String

    @SerializedName(value = "when", alternate = ["changed"])
    @Expose(serialize = false)
    var whenDate: String = ""
    /*get() {
        //2020-12-03T02:58:28.430498 default
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val d: Date = sdf.parse(field)
        //field = output.format(d)
        return output.format(d)
    }*/

    @SerializedName("title")
    @Expose(serialize = true, deserialize = true)
    lateinit var title: String

}