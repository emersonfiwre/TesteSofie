package com.emersonfiwre.testesofie.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class APIError(
    @SerializedName("message")
    @Expose(serialize = true, deserialize = true)
    var message:String = ""
)
