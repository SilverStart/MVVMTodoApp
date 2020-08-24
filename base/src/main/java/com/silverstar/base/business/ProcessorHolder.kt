package com.silverstar.base.business

import io.reactivex.rxjava3.core.ObservableTransformer

interface ProcessorHolder<U, D> {

    val processor: ObservableTransformer<U, D>
}