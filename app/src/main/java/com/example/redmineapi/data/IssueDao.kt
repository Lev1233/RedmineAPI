package com.example.redmineapi.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIssue(issueEntity: IssueEntity)


    @Query("SELECT * from issues WHERE id = :id")
    fun getIssue(id: Int): Flow<IssueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(issueEntities: List<IssueEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateIssue(issueEntity: IssueEntity)

    @Query("SELECT * from issues")
    fun getAllIssues(): Flow<List<IssueEntity>>

}