package com.example.redmineapi.network


import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET

import retrofit2.http.PUT
import retrofit2.http.Path


const val KEY = "key=4e0ac02e86a81dc063fcef2ab3bab12e2e4e588c"
interface RedmineApiService {

    @GET("issues.json?$KEY")
    suspend fun getIssue(): IssueResponse

 //   https://rm.wwind.ua/issues.json?key=4e0ac02e86a81dc063fcef2ab3bab12e2e4e588c
   // "https://rm.wwind.ua/issues/10958.json?key=4e0ac02e86a81dc063fcef2ab3bab12e2e4e588c"
   @PUT("issues/{id}.json?$KEY")
   suspend fun updateIssueStatus(
       @Path("id") issueId: Int,
       @Body statusUpdateRequest: StatusUpdateRequest
   ): Response<Void>
}

data class StatusUpdateRequest(
    @SerializedName("issue") val issue: StatusIssue
)

data class StatusIssue(
    @SerializedName("status_id") val statusId: Int
)

data class IssueResponse(
    val issues: List<Issue>,
    val total_count: Int,
    val offset: Int,
    val limit: Int
)


