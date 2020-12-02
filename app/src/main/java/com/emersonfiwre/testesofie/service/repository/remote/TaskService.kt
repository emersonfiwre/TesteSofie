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
}