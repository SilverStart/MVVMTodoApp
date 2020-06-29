package com.silverstar.mvvmtodoapp.vm.todo.input

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import com.silverstar.mvvmtodoapp.BR
import com.silverstar.mvvmtodoapp.business.base.ProcessorHolder
import com.silverstar.mvvmtodoapp.business.todo.input.SaveRequest
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@ActivityRetainedScoped
class TodoInputViewModel @Inject constructor(processorHolder: ProcessorHolder<SaveRequest, Result<Boolean>>) :
    BaseObservable() {

    private val _title = BehaviorSubject.createDefault<String>("")
    private val _content = BehaviorSubject.createDefault<String>("")

    private val _saveEvent = PublishSubject.create<SaveRequest>()

    val canSave: LiveData<Boolean> = Transformations.map(
        LiveDataReactiveStreams.fromPublisher(_title.toFlowable(BackpressureStrategy.LATEST))
    ) {
        !it.isNullOrEmpty()
    }

    val isSaved: LiveData<Boolean> = LiveDataReactiveStreams.fromPublisher(
        _saveEvent.compose(processorHolder.processor)
            .filter { it.isSuccess }
            .map { it.getOrNull()!! }
            .toFlowable(BackpressureStrategy.LATEST)
    )

    @Bindable
    fun getTitle(): String = _title.value

    fun setTitle(title: String) {
        if (_title.value == title) return

        _title.onNext(title)

        notifyPropertyChanged(BR.title)
    }

    @Bindable
    fun getContent(): String = _content.value

    fun setContent(content: String) {
        if (_content.value == content) return

        _content.onNext(content)

        notifyPropertyChanged(BR.content)
    }

    fun save() {
        _saveEvent.onNext(SaveRequest(_title.value, _content.value))
    }
}
