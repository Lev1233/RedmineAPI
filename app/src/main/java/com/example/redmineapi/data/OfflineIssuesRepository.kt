package com.example.redmineapi.data

import kotlinx.coroutines.flow.Flow

class OfflineIssuesRepository(private val issueDao:IssueDao):IssuesRepository {

    override suspend fun insertIssue(issueEntity: IssueEntity) = issueDao.insertIssue(issueEntity)

    override suspend fun insertAll(issueEntity: List<IssueEntity>) = issueDao.insertAll(issueEntity)

    override fun getAllItemsStream(): Flow<List<IssueEntity>> = issueDao.getAllIssues()

    override fun getItemStream(id: Int): Flow<IssueEntity?> = issueDao.getIssue(id)

    override suspend fun updateIssue(issueEntity: IssueEntity) = issueDao.updateIssue(issueEntity)


}