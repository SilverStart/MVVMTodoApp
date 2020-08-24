package com.silverstar.mvvmtodoapp.vm.todo.input

import InstantExecutorExtension
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.base.business.ProcessorHolder
import com.silverstar.mvvmtodoapp.business.todo.input.SaveTodoRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
@DisplayName("TodoInputViewModel 클래스")
class TodoInputViewModelTest {

    private val processorHolder = mock<ProcessorHolder<SaveTodoRequest, Result<Boolean>>>()

    private lateinit var viewModel: TodoInputViewModel

    @Nested
    @DisplayName("제목이 비어있을 경우")
    inner class ContextWithEmptyTitle {

        private val emptyTitle = ""

        @Test
        @DisplayName("canSave 는 false 를 리턴한다.")
        fun canSaveReturnsFalse() {
            mockProcessorHolder {
                it.switchMap {
                    Observable.just(Result.success(true))
                }
            }

            viewModel = TodoInputViewModel(processorHolder)

            viewModel.canSave.observeForever { }

            viewModel.setTitle(emptyTitle)

            assertEquals(false, viewModel.canSave.value)
        }
    }

    @Nested
    @DisplayName("제목이 비어있지 않을 경우")
    inner class ContextWithTitleFilled {

        private val titleFilled = "this is title."

        @Test
        @DisplayName("canSave 는 true 를 리턴한다.")
        fun canSaveReturnsTrue() {
            mockProcessorHolder {
                it.switchMap {
                    Observable.just(Result.success(true))
                }
            }

            viewModel = TodoInputViewModel(processorHolder)

            viewModel.canSave.observeForever { }

            viewModel.setTitle(titleFilled)

            assertEquals(true, viewModel.canSave.value)
        }
    }

    @Nested
    @DisplayName("save 메소드")
    inner class DescribeSaveMethod {

        @Nested
        @DisplayName("값이 채워진 제목과 내용과 함께 호출 시")
        inner class ContextWithFilledValues {
            private val titleFilled = "this is title."
            private val contentFilled = "this is content."

            @Test
            @DisplayName("isSaved 를 true 로 변경한다.")
            fun itChangesIsSavedTrue() {
                mockProcessorHolder {
                    it.switchMap {
                        Observable.just(Result.success(true))
                    }
                }

                viewModel = TodoInputViewModel(processorHolder)

                viewModel.isSaved.observeForever { }

                viewModel.setTitle(titleFilled)
                viewModel.setContent(contentFilled)

                viewModel.save()

                assertEquals(true, viewModel.isSaved.value)
            }
        }
    }

    private fun mockProcessorHolder(observableTransformer: ObservableTransformer<SaveTodoRequest, Result<Boolean>>) {
        whenever(processorHolder.processor).thenReturn(observableTransformer)
    }
}