package com.example.redmineapi.screen

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redmineapi.data.IssueEntity
import com.example.redmineapi.data.IssuesRepository
import com.example.redmineapi.data.RedmineRepository
import com.example.redmineapi.data.mapper.toIssueEntity
import com.example.redmineapi.network.Issue
import com.example.redmineapi.network.IssueResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response


class DetailIssueViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val issuesRepository: IssuesRepository,
    private val redmineRepository: RedmineRepository,

    ) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[DetailDestination.issueIdArg])


    val itemDetails: StateFlow<IssueEntity?> = issuesRepository.getItemStream(itemId)
        .catch { e->
           e.printStackTrace()
            emit(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateStatus(issueId: Int, newStatusId: Int) {
        viewModelScope.launch {

            try {
                val response: Response<Void> = redmineRepository.changeIssueStatus(issueId, newStatusId)
               
                  if(response.isSuccessful ){
                      val isuechange: IssueResponse = redmineRepository.getIssueResponse()

                      isuechange.issues.forEach { issue ->
                          Log.d("forEach", "DB" + issue.toString())
                          issuesRepository.updateIssue(issue.toIssueEntity())
                      }
                  }


            } catch (e: Exception) {
                Log.e("TAGI12", "Error during request processing", e)
                e.printStackTrace()
            }
        }
    }

}

