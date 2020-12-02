package com.emersonfiwre.testesofie.service.repository.remote

import com.emersonfiwre.testesofie.service.model.TaskListModel
import com.emersonfiwre.testesofie.service.model.TaskCreateModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskService {
    @GET("task")
    fun all(): Call<TaskListModel>

    @POST("task")
    @FormUrlEncoded
    fun create(
        @Field("title") title: String,
        @Field("email") email: String,
        @Field("description") description: String
    ): Call<TaskCreateModel>
}