package com.silverstar.mvvmtodoapp.di.scheduler

import com.silverstar.mvvmtodoapp.business.util.SchedulerProvider
import com.silverstar.mvvmtodoapp.business.util.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class SchedulerModule {
    @Binds
    abstract fun bindSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider
}