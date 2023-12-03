package com.example.redmineapi.screen

import android.util.Log
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope


import com.example.redmineapi.data.IssueEntity
import com.example.redmineapi.data.IssuesRepository
import com.example.redmineapi.data.RedmineRepository


import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


sealed interface IssuesUiState {
    data class Success(val issuesEntity: List<IssueEntity> = listOf()) : IssuesUiState
    object Error : IssuesUiState
    object Loading : IssuesUiState
}

class HomeScreenViewModel(
    private val redmineRepository: RedmineRepository,
    private val issuesRepository: IssuesRepository,

    ) : ViewModel() {

    private val _redmineUiState = MutableStateFlow<IssuesUiState>(IssuesUiState.Loading)
    val redmineUiState: StateFlow<IssuesUiState> get() = _redmineUiState.asStateFlow()

    private var lastSearch: String = ""
    // var redmineUiState: IssuesUiState by mutableStateOf(IssuesUiState.Loading)
    //   private set

    fun updateUiState(newState: IssuesUiState) {
        _redmineUiState.value = newState
    }

    fun getLastSearch(): String {
        return lastSearch
    }

    init {
        Log.d("TAG", "init")
        getTasks()

    }

    fun getTasks() {
        Log.d("TAG", "Entering getTasks()")
        _redmineUiState.value = IssuesUiState.Loading

//            val workRequest = PeriodicWorkRequestBuilder<RedmineWorker>(
//                15,
//                TimeUnit.MINUTES//main
//            )
//                .build()
//
//            WorkManager.getInstance().enqueue(workRequest)

        issuesRepository.getAllItemsStream()
            .catch { exception ->
                Log.e("TAG", "Error fetching issues: $exception")
                updateUiState(IssuesUiState.Error)
            }.onEach { issues: List<IssueEntity> ->
                Log.d("onEach", "issues: $issues")
                updateUiState(IssuesUiState.Success(issues))
            }.launchIn(viewModelScope)
    }

    fun searchIssue(text: String) {
        _redmineUiState.value = IssuesUiState.Loading

        // Збереження тексту пошуку
        lastSearch = text

        if (text.isBlank()) {
            // якщо текст пошуку порожній, відобразити увесь спісок завдань
            issuesRepository.getAllItemsStream()
                .onEach { issues ->
                    _redmineUiState.value = IssuesUiState.Success(issues)
                }
                .catch { exception ->
                    Log.e("TAG", "Error fetching issues: $exception")
                    _redmineUiState.value = IssuesUiState.Error
                }
                .launchIn(viewModelScope)
        } else {
            // відображення результату пошуку
            issuesRepository.getAllItemsStream()
                .map { issues ->
                    issues.filter {

                        it.subject?.contains(text, ignoreCase = true) == true ||
                        it.authorName?.contains(text, ignoreCase = true) == true
                    }
                }
                .onEach { filteredIssues ->
                    _redmineUiState.value = IssuesUiState.Success(filteredIssues)
                }
                .catch { exception ->
                    Log.e("TAG", "Error fetching filtered issues: $exception")
                    _redmineUiState.value = IssuesUiState.Error
                }
                .launchIn(viewModelScope)
        }
    }

}


data class IssueEntityDetails(
    val id: Int = 0,
    val projectName: String? = "",
    val authorName: String? = "",
    val subject: String? = "",
    val estimatedHours: Double? = 0.0
)

//        issuesRepository.getAllItemsStream()
//            .catch {exception->
//                Log.e("TAG", "Error fetching issues: $exception")
//                updateUiState(IssuesUiState.Error)
//            }.onEach { issues: List<IssueEntity> ->
//                Log.d("onEach", "Error fetching issues: $issues")
//                updateUiState(IssuesUiState.Success(issues))
//            }.launchIn(viewModelScope)