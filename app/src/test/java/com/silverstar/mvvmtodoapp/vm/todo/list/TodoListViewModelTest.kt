package com.silverstar.mvvmtodoapp.vm.todo.list

import InstantExecutorExtension
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.base.business.ProcessorHolder
import com.silverstar.mvvmtodoapp.data.entity.Todo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
@DisplayName("TodoListViewModel 클래스")
class TodoListViewModelTest {

    private lateinit var viewModel: TodoListViewModel

    private val processorHolder: ProcessorHolder<Unit, Result<List<Todo>>> = mock()

    @Nested
    @DisplayName("todoList 프로퍼티")
    inner class DescribeTodoList {

        @Nested
        @DisplayName("정상적인 할 일 목록이 리턴되면")
        inner class ContextWithCorrectTodoListMock {
            private val todoList = listOf(Todo("title1", "content1"))

            @BeforeEach
            fun setUp() {
                whenever(processorHolder.processor).thenReturn(
                    ObservableTransformer {
                        it.switchMap { Observable.just(Result.success(todoList)) }
                    }
                )
            }

            @Test
            @DisplayName("동일한 할 일 목록을 리턴한다")
            fun itReturnsSameTodoList() {
                viewModel = TodoListViewModel(processorHolder)

                viewModel.todoList.observeForever { }

                viewModel.loadTodoList()

                assertEquals(todoList, viewModel.todoList.value)
            }
        }
    }

    @Nested
    @DisplayName("isFailedLoading 프로퍼티")
    inner class DescribeIsFailedLoading {

        @Nested
        @DisplayName("에러가 리턴되면")
        inner class ContextWithError {
            private val error = IllegalArgumentException("a error!!!")

            @BeforeEach
            fun setUp() {
                whenever(processorHolder.processor).thenReturn(
                    ObservableTransformer {
                        it.switchMap { Observable.just(Result.failure<List<Todo>>(error)) }
                    }
                )
            }

            @Test
            @DisplayName("이벤트를 리턴한다")
            fun itReturnsErrorResult() {
                viewModel = TodoListViewModel(processorHolder)

                viewModel.isFailedLoading.observeForever { }

                viewModel.loadTodoList()

                assertEquals(Unit, viewModel.isFailedLoading.value?.getIfNotHandled())
            }

        }
    }
}