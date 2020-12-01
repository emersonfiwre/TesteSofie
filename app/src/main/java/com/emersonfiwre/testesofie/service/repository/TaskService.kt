package com.emersonfiwre.testesofie.service.repository

import com.emersonfiwre.testesofie.service.model.TaskModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskService {
    @GET("Task")
    fun all(): Call<List<TaskModel>>

    @POST("Task")
    @FormUrlEncoded
    fun create(
        @Field("title") title: String,
        @Field("email") email: String,
        @Field("description") description: String
    ): Call<TaskModel>
}