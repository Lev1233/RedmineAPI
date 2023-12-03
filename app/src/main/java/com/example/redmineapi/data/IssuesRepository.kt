package com.example.redmineapi.data

import kotlinx.coroutines.flow.Flow

interface IssuesRepository {

    fun  getAllItemsStream(): Flow<List<IssueEntity>>

    fun getItemStream(id: Int): Flow<IssueEntity?>

    suspend fun updateIssue(issueEntity: IssueEntity)

    suspend fun insertAll(issueEntity:  List<IssueEntity>)

    suspend fun insertIssue(issueEntity: IssueEntity)
}
