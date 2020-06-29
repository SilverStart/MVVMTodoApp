package com.silverstar.mvvmtodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverstar.mvvmtodoapp.vm.todo.input.TodoInputViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: TodoInputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}