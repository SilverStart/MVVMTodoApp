package com.silverstar.mvvmtodoapp.vm.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.silverstar.mvvmtodoapp.business.base.ProcessorHolder
import com.silverstar.mvvmtodoapp.data.entity.Todo
import com.silverstar.mvvmtodoapp.vm.util.Event
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.PublishSubject

class TodoListViewModel constructor(processorHolder: ProcessorHolder<Unit, Result<List<Todo>>>) {

    private val _loadTodoListRequest = PublishSubject.create<Unit>()

    private val _loadTodoListResult: Flowable<Result<List<Todo>>> =
        _loadTodoListRequest
            .compose(processorHolder.processor)
            .share()
            .toFlowable(BackpressureStrategy.LATEST)

    val todoList: LiveData<List<Todo>> = LiveDataReactiveStreams.fromPublisher(
        _loadTodoListResult
            .filter { it.isSuccess }
            .map { it.getOrNull()!! }
    )

    val isFailedLoading: LiveData<Event<Unit>> = LiveDataReactiveStreams.fromPublisher(
        _loadTodoListResult
            .filter { it.isFailure }
            .map { Event(Unit) }
    )

    fun loadTodoList() {
        _loadTodoListRequest.onNext(Unit)
    }
}
