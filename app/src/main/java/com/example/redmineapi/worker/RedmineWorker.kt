package com.example.redmineapi.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.redmineapi.RedmineApplication


import com.example.redmineapi.data.IssuesRepository
import com.example.redmineapi.data.RedmineRepository
import com.example.redmineapi.data.mapper.toIssueEntity
import com.example.redmineapi.network.IssueResponse
import com.example.redmineapi.screen.HomeScreenViewModel
import com.example.redmineapi.screen.IssuesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext


class RedmineWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val redmineRepository: RedmineRepository,
    private val issuesRepository: IssuesRepository,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d("TAG", "doWork")
            val issueResponse: IssueResponse = redmineRepository.getIssueResponse()
            Log.d("TAG", issueResponse.issues.toString())

            issueResponse.issues.forEach { issue ->
                Log.d("TAG", "DB" + issue.toString())
                issuesRepository.insertIssue(issue.toIssueEntity())
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("RedmineWorker", "Error in doWork: $e")
            Result.failure()
        }
    }

}


