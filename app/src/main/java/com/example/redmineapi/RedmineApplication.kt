package com.example.redmineapi

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.redmineapi.data.AppContainer
import com.example.redmineapi.data.DefaultAppContainer
import com.example.redmineapi.data.IssuesRepository
import com.example.redmineapi.worker.RedmineWorker
import java.util.concurrent.TimeUnit


class RedmineApplication: Application() {

    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)

        WorkManager.initialize(this, (container as DefaultAppContainer).getWorkManagerConfiguration())
        val workRequest = PeriodicWorkRequestBuilder<RedmineWorker>(
            15,
            TimeUnit.MINUTES
        )
            .build()

        WorkManager.getInstance().enqueue(workRequest)
    }

}


