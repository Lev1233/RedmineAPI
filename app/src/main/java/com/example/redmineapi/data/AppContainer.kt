package com.example.redmineapi.data

import android.content.Context
import androidx.work.Configuration
import com.example.redmineapi.network.RedmineApiService
import com.example.redmineapi.worker.RedmineWorkerFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val redmineRepository: RedmineRepository
    val issuesRepository: IssuesRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer,Configuration.Provider {

    // Создаем объект interceptor и устанавливаем уровень логирования
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
 val client  = OkHttpClient.Builder()
     .addInterceptor(interceptor)
     .build()


    private val baseUrl =
        "https://rm.wwind.ua"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitService: RedmineApiService by lazy {
        retrofit.create(RedmineApiService::class.java)
    }

    override val redmineRepository: RedmineRepository by lazy {
        NetworkRedmineRepository(retrofitService)
    }
    override val issuesRepository: IssuesRepository by lazy {
        OfflineIssuesRepository(RedmineDataBase.getDatabase(context).issueDao())
    }


    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(
                RedmineWorkerFactory(redmineRepository,issuesRepository)
            )
            .build()

}

