package com.silverstar.mvvmtodoapp.di.dao

import com.silverstar.mvvmtodoapp.data.dao.TodoDao
import com.silverstar.mvvmtodoapp.data.dao.TodoDaoTemp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DaoModule {

    @Binds
    abstract fun bindTodoDao(todoDaoTemp: TodoDaoTemp): TodoDao
}