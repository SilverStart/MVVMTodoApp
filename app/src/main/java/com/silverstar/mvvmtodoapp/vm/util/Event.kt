package com.silverstar.mvvmtodoapp.vm.util

class Event<T>(private val content: T) {

    var isHandled: Boolean = false
        private set

    fun getIfNotHandled(): T? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            content
        }
    }
}