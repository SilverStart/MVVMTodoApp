package com.silverstar.mvvmtodoapp.vm.todo.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.silverstar.mvvmtodoapp.business.base.ProcessorHolder
import com.silverstar.mvvmtodoapp.data.entity.Todo
import com.silverstar.mvvmtodoapp.vm.util.Event
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class TodoDetailViewModel constructor(loadTodoProcessorHolder: ProcessorHolder<Int, Result<Todo>>) {

    private val _loadTodoRequest = BehaviorSubject.create<Int>()

    private val _loadTodoResult: Flowable<Result<Todo>> = _loadTodoRequest
        .compose(loadTodoProcessorHolder.processor)
        .publish()
        .autoConnect()
        .toFlowable(BackpressureStrategy.LATEST)

    val todo: LiveData<Todo> = LiveDataReactiveStreams.fromPublisher(
        _loadTodoResult
            .filter { it.isSuccess }
            .map { it.getOrNull()!! }
    )

    val isFailedLoading: LiveData<Event<Unit>> = LiveDataReactiveStreams.fromPublisher(
        _loadTodoResult
            .filter { it.isFailure }
            .map { Event(Unit) }
    )

    fun load(id: Int) {
        _loadTodoRequest.onNext(id)
    }
}
