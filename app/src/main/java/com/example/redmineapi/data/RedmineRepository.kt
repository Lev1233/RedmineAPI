package com.example.redmineapi.data


import com.example.redmineapi.network.IssueResponse
import retrofit2.Response

interface RedmineRepository {
    suspend fun getIssueResponse(): IssueResponse

    suspend fun changeIssueStatus(issueId: Int, newStatusId: Int): Response<Void>


}