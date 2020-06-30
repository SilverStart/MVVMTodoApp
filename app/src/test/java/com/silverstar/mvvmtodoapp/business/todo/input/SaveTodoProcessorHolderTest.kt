package com.silverstar.mvvmtodoapp.business.todo.input

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.silverstar.mvvmtodoapp.data.dao.TodoDao
import com.silverstar.mvvmtodoapp.util.TestSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SaveTodoProcessorHolder 클래스")
class SaveTodoProcessorHolderTest {

    private lateinit var processorHolder: SaveTodoProcessorHolder

    private val schedulerProvider = TestSchedulerProvider()

    private val todoDao: TodoDao = mock()

    @Nested
    @DisplayName("에러가 없을 경우")
    inner class ContextWithNoIssue {

        @Test
        @DisplayName("성공 결과를 리턴한다")
        fun itReturnsTrueInSuccess() {
            processorHolder = SaveTodoProcessorHolder(todoDao, schedulerProvider)

            Observable.just(SaveRequest("title", "content"))
                .compose(processorHolder.processor)
                .test()
                .assertValue(Result.success(true))
        }
    }

    @Nested
    @DisplayName("에러가 있을 경우")
    inner class ContextWithError {

        private val error = IllegalArgumentException("a error!!!")

        @BeforeEach
        fun setUp() {
            whenever(todoDao.insertTodo(any())).thenThrow(error)
        }

        @Test
        @DisplayName("실패 결과를 리턴한다")
        fun itReturnsFalseInFailure() {
            processorHolder = SaveTodoProcessorHolder(todoDao, schedulerProvider)

            Observable.just(SaveRequest("title", "content"))
                .compose(processorHolder.processor)
                .test()
                .assertValue(Result.failure(error))
        }
    }
}