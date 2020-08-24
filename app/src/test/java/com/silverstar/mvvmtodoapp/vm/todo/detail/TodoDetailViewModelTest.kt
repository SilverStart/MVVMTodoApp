package com.silverstar.mvvmtodoapp.vm.todo.detail

import InstantExecutorExtension
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.base.business.ProcessorHolder
import com.silverstar.mvvmtodoapp.data.entity.Todo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
@DisplayName("TodoDetailViewModel 클래스")
class TodoDetailViewModelTest {

    private lateinit var viewModel: TodoDetailViewModel

    private val processorHolder: ProcessorHolder<Int, Result<Todo>> = mock()

    @Nested
    @DisplayName("Todo 프로퍼티")
    inner class DescribeTodo {

        @Nested
        @DisplayName("정상적인 할 일이 리턴 될 경우")
        inner class ContextWithCorrectTodo {
            private val response = Todo("title", "content")

            @Test
            @DisplayName("동일한 할 일을 리턴한다")
            fun itReturnsSameTodo() {
                whenever(processorHolder.processor)
                    .thenReturn(
                        ObservableTransformer {
                            it.switchMap {
                                Observable.just(Result.success(response))
                            }
                        }
                    )

                viewModel = TodoDetailViewModel(processorHolder)

                viewModel.loadedData.observeForever { }

                viewModel.load(1)

                assertEquals(response, viewModel.loadedData.value)
            }
        }
    }

    @Nested
    @DisplayName("IsFailedLoading 프로퍼티")
    inner class DescribeIsFailedLoading {

        @Nested
        @DisplayName("에러가 발생할 경우")
        inner class ContextWithError {
            private val error = IllegalArgumentException("a error!!!")

            @Test
            @DisplayName("해당 이벤트가 갱신된다")
            fun itIsOccurred() {
                whenever(processorHolder.processor)
                    .thenReturn(
                        ObservableTransformer {
                            it.switchMap {
                                Observable.just(Result.failure<Todo>(error))
                            }
                        }
                    )

                viewModel = TodoDetailViewModel(processorHolder)

                viewModel.isFailedLoading.observeForever { }

                viewModel.load(1)

                assertEquals(Unit, viewModel.isFailedLoading.value?.getIfNotHandled())
            }
        }
    }
}