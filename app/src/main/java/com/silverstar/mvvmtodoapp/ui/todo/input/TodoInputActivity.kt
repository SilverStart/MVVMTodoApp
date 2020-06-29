package com.silverstar.mvvmtodoapp.ui.todo.input

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.silverstar.mvvmtodoapp.R
import com.silverstar.mvvmtodoapp.databinding.ActivityTodoInputBinding
import com.silverstar.mvvmtodoapp.vm.todo.input.TodoInputViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoInputActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: TodoInputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoInputBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todo_input)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.isSaved.observe(this, Observer {
            if (it) {
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}