package com.silverstar.mvvmtodoapp.business.todo.input

import com.silverstar.mvvmtodoapp.data.dao.TodoDao
import com.silverstar.mvvmtodoapp.data.entity.Todo
import com.silverstar.base.business.ProcessorHolder
import com.silverstar.base.business.util.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import javax.inject.Inject

class SaveTodoProcessorHolder @Inject constructor(
    todoDao: TodoDao,
    schedulerProvider: SchedulerProvider
) : ProcessorHolder<@JvmSuppressWildcards SaveTodoRequest, @JvmSuppressWildcards Result<Boolean>> {

    override val processor: ObservableTransformer<SaveTodoRequest, Result<Boolean>> =
        ObservableTransformer { source ->
            source.switchMap {
                Observable
                    .fromCallable { todoDao.insertTodo(Todo(it.title, it.content)) }
                    .map { Result.success(true) }
                    .onErrorReturn { Result.failure(it) }
                    .subscribeOn(schedulerProvider.io())
            }.observeOn(schedulerProvider.ui())
        }
}