package com.example.mazen.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.Toast
import com.example.mazen.todo.data.TodoDatabaseHandler
import com.example.mazen.todo.data.listAdapter
import com.example.mazen.todo.model.Todo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var db: TodoDatabaseHandler? = null
    private var adapter: listAdapter? = null
    private var todoList: ArrayList<Todo>? = null
    private var layoutmgr: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val entertodo = enterTodoId
        db = TodoDatabaseHandler(this)

        todoList = ArrayList<Todo>()

        layoutmgr = LinearLayoutManager(this)
        adapter = listAdapter(todoList!!, this)

        recyclerView.layoutManager = layoutmgr
        recyclerView.adapter = adapter

        adapter!!.notifyDataSetChanged()
        updateView()

        saveTodoButtonId.setOnClickListener{

            if(!TextUtils.isEmpty(entertodo.text)){

                val item = Todo()

                item.todoName = entertodo.text.toString().capitalize()
                item.todoDate = System.currentTimeMillis()
                item.formattedDate = item.showHumanDate(System.currentTimeMillis())

                db!!.createTodo(item)
                entertodo.setText("")

                todoList = db!!.readTodo()

                updateView()

            }else{

                Toast.makeText(this, "Enter a To-Do name...", Toast.LENGTH_LONG).show()

            }
        }
    }

    fun updateView(){

        todoList = db!!.readTodo()

        adapter = listAdapter(todoList!!, this)
        adapter!!.notifyDataSetChanged()
        recyclerView.adapter = adapter

    }
}