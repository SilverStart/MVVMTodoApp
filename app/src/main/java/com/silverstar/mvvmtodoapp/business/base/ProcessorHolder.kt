package com.silverstar.mvvmtodoapp.business.base

import io.reactivex.rxjava3.core.ObservableTransformer

interface ProcessorHolder<U, D> {

    val processor: ObservableTransformer<U, D>
}