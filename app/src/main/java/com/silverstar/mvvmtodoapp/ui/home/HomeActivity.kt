package com.silverstar.mvvmtodoapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverstar.mvvmtodoapp.R
import com.silverstar.mvvmtodoapp.ui.todo.input.TodoInputActivity
import com.silverstar.mvvmtodoapp.ui.todo.list.TodoListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_todo_input.setOnClickListener {
            startActivity(Intent(this, TodoInputActivity::class.java))
        }

        btn_todo_list.setOnClickListener {
            startActivity(Intent(this, TodoListActivity::class.java))
        }
    }
}