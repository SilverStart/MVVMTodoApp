package com.silverstar.mvvmtodoapp.data.dao

import com.silverstar.mvvmtodoapp.data.entity.Todo

interface TodoDao {

    fun insertTodo(todo: Todo)
}