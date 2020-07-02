package com.silverstar.mvvmtodoapp.vm.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.silverstar.mvvmtodoapp.business.base.ProcessorHolder
import com.silverstar.mvvmtodoapp.data.entity.Todo
import com.silverstar.mvvmtodoapp.vm.util.Event
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@ActivityRetainedScoped
class TodoListViewModel @Inject constructor(processorHolder: ProcessorHolder<Unit, Result<List<Todo>>>) {

    private val _loadTodoListRequest = BehaviorSubject.create<Unit>()

    private val _loadTodoListResult: Flowable<Result<List<Todo>>> =
        _loadTodoListRequest
            .compose(processorHolder.processor)
            .publish()
            .autoConnect()
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

    init {
        loadTodoList()
    }
}
