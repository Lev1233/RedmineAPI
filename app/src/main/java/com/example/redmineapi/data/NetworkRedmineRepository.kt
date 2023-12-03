package com.example.redmineapi.data
import com.example.redmineapi.network.IssueResponse
import com.example.redmineapi.network.RedmineApiService

import com.example.redmineapi.network.StatusIssue
import com.example.redmineapi.network.StatusUpdateRequest
import retrofit2.Response


class NetworkRedmineRepository(private val redmineApiService: RedmineApiService) : RedmineRepository {

    override suspend fun getIssueResponse(): IssueResponse = redmineApiService.getIssue()


    override suspend fun changeIssueStatus(issueId: Int, newStatusId: Int): Response<Void> {
        val newStatus = StatusUpdateRequest(StatusIssue(newStatusId))
        return redmineApiService.updateIssueStatus(issueId, newStatus)
    }

}



