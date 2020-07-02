package com.silverstar.mvvmtodoapp.di.business

import com.silverstar.mvvmtodoapp.business.base.ProcessorHolder
import com.silverstar.mvvmtodoapp.business.todo.input.SaveTodoProcessorHolder
import com.silverstar.mvvmtodoapp.business.todo.input.SaveTodoRequest
import com.silverstar.mvvmtodoapp.business.todo.list.LoadTodoListProcessorHolder
import com.silverstar.mvvmtodoapp.data.entity.Todo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProcessorHolderModule {

    @Binds
    abstract fun bindSaveTodoProcessorHolder(saveTodoProcessorHolder: SaveTodoProcessorHolder): ProcessorHolder<SaveTodoRequest, Result<Boolean>>

    @Binds
    abstract fun bindLoadTdoListProcessorHolder(loadTodoListProcessorHolder: LoadTodoListProcessorHolder): ProcessorHolder<Unit, Result<List<Todo>>>
}