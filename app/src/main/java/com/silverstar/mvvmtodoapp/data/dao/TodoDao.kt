package com.silverstar.mvvmtodoapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.silverstar.mvvmtodoapp.data.entity.Todo

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(vararg todo: Todo)
}