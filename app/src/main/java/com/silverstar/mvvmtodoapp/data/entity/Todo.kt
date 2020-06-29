package com.silverstar.mvvmtodoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val content: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)