package com.silverstar.base.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.silverstar.base.business.ProcessorHolder
import com.silverstar.base.util.Event
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseViewModel<U, D>(
    processorHolder: ProcessorHolder<U, Result<D>>
) {

    private val request = BehaviorSubject.create<U>()

    private val result: Flowable<Result<D>> =
        request
            .compose(processorHolder.processor)
            .toFlowable(BackpressureStrategy.LATEST)
            .publish()
            .autoConnect()

    val loadedData: LiveData<D> = LiveDataReactiveStreams.fromPublisher(
        result
            .filter { it.isSuccess && it.getOrNull() != null }
            .map { it.getOrNull()!! }
    )

    val isFailedLoading: LiveData<Event<Unit>> = LiveDataReactiveStreams.fromPublisher(
        result
            .filter { it.isFailure }
            .map { Event(Unit) }
    )

    protected fun getResult(): Flowable<Result<D>> = result

    fun execute(value: U) {
        request.onNext(value)
    }
}