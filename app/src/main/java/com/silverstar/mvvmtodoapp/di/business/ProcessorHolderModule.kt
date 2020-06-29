package com.silverstar.mvvmtodoapp.di.business

import com.silverstar.mvvmtodoapp.business.base.ProcessorHolder
import com.silverstar.mvvmtodoapp.business.todo.input.SaveRequest
import com.silverstar.mvvmtodoapp.business.todo.input.SaveTodoProcessorHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProcessorHolderModule {

    @Binds
    abstract fun bindSaveTodoProcessorHolder(saveTodoProcessorHolder: SaveTodoProcessorHolder): ProcessorHolder<SaveRequest, Result<Boolean>>
}