package com.silverstar.mvvmtodoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silverstar.mvvmtodoapp.data.dao.TodoDao
import com.silverstar.mvvmtodoapp.data.entity.Todo

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Todo::class
    ]
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}