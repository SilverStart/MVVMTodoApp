package com.silverstar.mvvmtodoapp.vm.todo.detail

import com.silverstar.base.business.ProcessorHolder
import com.silverstar.base.vm.BaseViewModel
import com.silverstar.mvvmtodoapp.data.entity.Todo

class TodoDetailViewModel constructor(loadTodoProcessorHolder: ProcessorHolder<Int, Result<Todo>>) :
    BaseViewModel<Int, Todo>(loadTodoProcessorHolder) {
    fun load(id: Int) {
        execute(id)
    }
}
