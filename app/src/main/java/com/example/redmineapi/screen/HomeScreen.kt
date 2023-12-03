package com.example.redmineapi.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.redmineapi.AppViewModelProvider
import com.example.redmineapi.R
import com.example.redmineapi.navigation.NavigationDestination

import com.example.redmineapi.data.IssueEntity


import com.example.redmineapi.ui.theme.RedmineApiTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val issuesUiState: IssuesUiState by viewModel.redmineUiState.collectAsState()

    val searchText = remember {
        mutableStateOf(viewModel.getLastSearch())
    }
    Scaffold(
        // modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),

                query = searchText.value,
                onQueryChange = { text ->
                    searchText.value = text
                    viewModel.searchIssue(text)
                },
                onSearch = { text ->

                },
                placeholder = {
                    Text(text = "Search...")
                },
                active = false,
                onActiveChange = {

                }
            ) {

            }
        }
    ) { innerPadding ->

        when (val currentState: IssuesUiState = issuesUiState) {

            is IssuesUiState.Loading -> LoadingScreen(modifier.size(200.dp))
            is IssuesUiState.Success ->
                TasksListScreen(
                    modifier = modifier.padding(innerPadding),
                    onItemClick = navigateToDetail,
                    issues = currentState.issuesEntity,
                    contentPadding = contentPadding
                )

            else -> {
                ErrorScreen(retryAction, modifier)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TasksListScreen(
    onItemClick: (Int) -> Unit,
    issues: List<IssueEntity>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items = issues, key = { issue -> issue.id }
        ) { issue ->
            TaskCard(issue = issue,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(issue.id) })
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.load),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}


@Composable
fun TaskCard(issue: IssueEntity, modifier: Modifier = Modifier) {

    val priorityColor = when (issue.priority) {
        "Высокий" -> MaterialTheme.colorScheme.inversePrimary
        else -> MaterialTheme.colorScheme.secondaryContainer
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    )
    {
        Column(
            modifier = Modifier.background(priorityColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = issue.id.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                text = issue.subject ?: "NULL",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                text = issue.projectName ?: "NULL",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                text = issue.authorName ?: "NULL",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Text(
                text = issue.estimatedHours.toString(),
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

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    RedmineApiTheme {
        val sampleIssue = IssueEntity(
            id = 1,
            projectName = "01 Тестовий проект",
            authorName = "Курляндський Лев",
            subject = "Текст завдання",
            estimatedHours = 5.0,
            tracker = "tracker",
            priority = "priority",
            description = "description",
            status = "status",
            version = "version"

        )
        TasksListScreen(
            onItemClick = {},
            issues = listOf(sampleIssue, sampleIssue.copy(id = 2), sampleIssue.copy(id = 3)),
            modifier = Modifier,
            contentPadding = PaddingValues(0.dp),
        )
    }
}