package com.example.redmineapi.screen

import android.annotation.SuppressLint
import android.content.Context

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.redmineapi.AppViewModelProvider
import com.example.redmineapi.R
import com.example.redmineapi.navigation.NavigationDestination

import kotlinx.coroutines.delay

import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext




object DetailDestination : NavigationDestination {
    override val route = "issue_details"
    override val titleRes = R.string.issue_detail_title
    const val issueIdArg = "issueId"
    val routeWithArgs: String = "$route/{$issueIdArg}"
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@Composable
fun DetailIssueScreen(
    modifier:Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DetailIssueViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val itemDetailsState by viewModel.itemDetails.collectAsState(initial = null)

//    val itemDetailsState by viewModel.itemDetails
//        .onEach { issueEntity ->
//            Log.d("DetailIssueScreen", "itemDetailsState updated: $issueEntity")
//        }
//        .collectAsState(initial = null)

    val snackbarVisibleState = remember { mutableStateOf(false) }

    val snackbarMessageState = remember { mutableStateOf("") }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    val showSnackbar: (String) -> Unit = { message ->
        snackbarMessageState.value = message
        snackbarVisibleState.value = true
    }

    // ефєет закриває SanackBar
    LaunchedEffect(snackbarVisibleState.value) {
        if (snackbarVisibleState.value) {
            delay(1000)
            snackbarVisibleState.value = false
            navigateBack()
        }
    }

    val priorityColor = when (itemDetailsState?.priority) {
        "Высокий" -> MaterialTheme.colorScheme.inversePrimary
        else -> MaterialTheme.colorScheme.secondaryContainer
    }


    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())

    ) {
        Column(
            modifier = Modifier.background(priorityColor),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            val context = LocalContext.current
            Button(
                onClick = {
                    if (isNetworkAvailable(context)
                    ) {
                        viewModel.updateStatus(itemDetailsState?.id ?: 0, 3)
//                    val workRequest = OneTimeWorkRequestBuilder<RedmineWorker>().build()
//                    WorkManager.getInstance().enqueue(workRequest)
                        showSnackbar("Статус оновлено")
                    }else {
                        showSnackbar("Немає інтернету ")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text("Виконано")
            }

            // Snackbar
            if (snackbarVisibleState.value) {
                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(snackbarMessageState.value)
                }
            }

                Text(
                    text = itemDetailsState?.id.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = itemDetailsState?.subject ?: "NULL",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = itemDetailsState?.projectName ?: "NULL",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = itemDetailsState?.authorName ?: "NULL",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = itemDetailsState?.estimatedHours.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = itemDetailsState?.tracker.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = itemDetailsState?.priority.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = itemDetailsState?.description.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = itemDetailsState?.status.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = itemDetailsState?.version.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )


        }
    }
}