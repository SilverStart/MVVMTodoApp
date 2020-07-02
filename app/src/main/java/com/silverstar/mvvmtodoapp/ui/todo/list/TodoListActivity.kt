package com.silverstar.mvvmtodoapp.ui.todo.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.silverstar.mvvmtodoapp.R
import com.silverstar.mvvmtodoapp.databinding.ActivityTodoListBinding
import com.silverstar.mvvmtodoapp.ui.todo.list.adapter.TodoListAdapter
import com.silverstar.mvvmtodoapp.vm.todo.list.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: TodoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todo_list)

        val adapter = TodoListAdapter()

        binding.rvTodoList.adapter = adapter

        viewModel.todoList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}