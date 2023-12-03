package com.example.redmineapi.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

import com.example.redmineapi.data.IssuesRepository
import com.example.redmineapi.data.RedmineRepository

class RedmineWorkerFactory(
    private val redmineRepository: RedmineRepository,
    private val issuesRepository: IssuesRepository,
    ) : WorkerFactory()

{
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("TAG", "createWorker")
        return  RedmineWorker(appContext,workerParameters,redmineRepository,issuesRepository,)
    }
}

