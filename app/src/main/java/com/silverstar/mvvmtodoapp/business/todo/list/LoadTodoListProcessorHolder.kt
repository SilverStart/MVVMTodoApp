package com.silverstar.mvvmtodoapp.business.todo.list

import com.silverstar.base.business.ProcessorHolder
import com.silverstar.base.business.util.SchedulerProvider
import com.silverstar.mvvmtodoapp.data.dao.TodoDao
import com.silverstar.mvvmtodoapp.data.entity.Todo
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.ObservableTransformer
import javax.inject.Inject

class LoadTodoListProcessorHolder @Inject constructor(
    todoDao: TodoDao,
    schedulerProvider: SchedulerProvider
) :
    ProcessorHolder<@JvmSuppressWildcards Unit, @JvmSuppressWildcards Result<List<Todo>>> {

    override val processor: ObservableTransformer<Unit, Result<List<Todo>>> =
        ObservableTransformer { source ->
            source.switchMap {
                todoDao.getAll()
                    .`as`(RxJavaBridge.toV3Observable())
                    .map { Result.success(it) }
                    .subscribeOn(schedulerProvider.io())
            }
                .observeOn(schedulerProvider.ui())
        }
}