package com.example.redmineapi

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.redmineapi.screen.DetailIssueViewModel
import com.example.redmineapi.screen.HomeScreenViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                redmineApplication().container.redmineRepository,
                redmineApplication().container.issuesRepository,
                )
        }
        initializer {
            DetailIssueViewModel(
                this.createSavedStateHandle(),
                 redmineApplication().container.issuesRepository,
                 redmineApplication().container.redmineRepository
            )
        }
        }

    }
/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.redmineApplication(): RedmineApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RedmineApplication)