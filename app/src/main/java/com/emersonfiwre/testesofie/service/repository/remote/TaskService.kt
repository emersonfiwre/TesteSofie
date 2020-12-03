package com.emersonfiwre.testesofie.service.repository.remote

import com.emersonfiwre.testesofie.service.model.TaskListModel
import com.emersonfiwre.testesofie.service.model.TaskCreateModel
import com.emersonfiwre.testesofie.service.model.TaskModel
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @GET("task")
    fun all(): Call<TaskListModel>

    @POST("task")
    fun create(
        @Body body: TaskModel
    ): Call<TaskCreateModel>

    @HTTP(method = "PUT", path = "task/{id}", hasBody = true)
    fun update(
        @Path(value = "id", encoded = true) id: String,
        @Body body: TaskModel
    ): Call<TaskCreateModel>

    @HTTP(method = "DELETE", path = "task/{id}", hasBody = true)
    fun delete(
        @Path(value = "id", encoded = true) id: String
    ): Call<TaskCreateModel>

    @HTTP(method = "PATCH", path = "task/{id}", hasBody = true)
    fun complete(
        @Path(value = "id") id: String,
        @Body body: TaskModel
    ): Call<TaskCreateModel>


}