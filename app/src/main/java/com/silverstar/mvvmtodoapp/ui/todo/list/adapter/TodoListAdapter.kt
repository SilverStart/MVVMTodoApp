package com.silverstar.mvvmtodoapp.ui.todo.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.silverstar.mvvmtodoapp.R
import com.silverstar.mvvmtodoapp.data.entity.Todo
import com.silverstar.mvvmtodoapp.ui.base.adapter.DataBindingAdapter

class TodoListAdapter : DataBindingAdapter<Todo>(TodoDiffItemCallback()) {

    override fun getItemViewType(position: Int): Int = R.layout.item_todo
}

class TodoDiffItemCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean =
        oldItem.title == newItem.title && oldItem.content == newItem.content
}