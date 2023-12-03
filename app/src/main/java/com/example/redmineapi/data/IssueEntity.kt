package com.example.redmineapi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "issues")
data class IssueEntity(
    @PrimaryKey val id: Int,//Номер задачі,
    @ColumnInfo(name = "project") val projectName: String?,//Проєкт,
    @ColumnInfo(name = "author") val authorName: String?,//Автор
    @ColumnInfo(name = "subject") val subject: String?,//Тема
    @ColumnInfo(name = "hours") val estimatedHours: Double?,//Оцінка в годинах

    @ColumnInfo(name = "tracker") val tracker: String?,//Трекер
    @ColumnInfo(name = "priority") val priority: String?,//Пріорітет
    @ColumnInfo(name = "fixed_version") val version: String?,//Версія
    @ColumnInfo(name = "description") val description: String?,//Опис
    @ColumnInfo(name = "status") val status: String?,//Статус
)

//Статус

