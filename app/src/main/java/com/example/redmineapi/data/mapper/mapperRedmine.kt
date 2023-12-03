package com.example.redmineapi.data.mapper

import com.example.redmineapi.data.IssueEntity
import com.example.redmineapi.network.Issue

fun Issue.toIssueEntity(): IssueEntity = IssueEntity(
    id = id,
    projectName = project?.name,
    authorName = author?.name,
    subject = subject,
    estimatedHours = estimatedHours,
    tracker = tracker?.name,
    priority = priority?.name,
    description = description,
    status = status?.name,
    version = version?.name
)



