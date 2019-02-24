package com.example.mazen.todo.data

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mazen.todo.R
import com.example.mazen.todo.model.Todo
import kotlinx.android.synthetic.main.editpopup.view.*

class listAdapter(private val list: ArrayList<Todo>, private val context: Context): RecyclerView.Adapter<listAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): listAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)

        return ViewHolder(view, context, list)

    }

    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: listAdapter.ViewHolder, position: Int) {

        holder.bindViews(list[position])

    }

    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Todo>): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var mcontext = context
        var mlist = list

        var todoname = itemView.findViewById(R.id.listNameId) as TextView
        var todoDate = itemView.findViewById(R.id.listDate) as TextView
        var todoDel = itemView.findViewById(R.id.listDeleteButton) as Button
        var todoEdit = itemView.findViewById(R.id.listEditButton) as Button

        fun bindViews(todo: Todo){

            todoname.text = todo.todoName
            todoDate.text = todo.formattedDate

            todoDel.setOnClickListener(this)
            todoEdit.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

            val mposition: Int = adapterPosition
            val clone = mlist[mposition]
            when(v!!.id){

                todoDel.id -> {

                    deleteTodo(clone.todoId!!)
                    mlist.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)

                }

                todoEdit.id -> {

                    editTodo(clone)

                }

            }
        }

        fun deleteTodo(id: Int){

            val db: TodoDatabaseHandler = TodoDatabaseHandler(mcontext)
            db.deleteTodo(id)

        }

        fun editTodo(item: Todo){

            val mposition: Int = adapterPosition
            val clone = mlist[mposition]

            val layoutInflater: LayoutInflater? = LayoutInflater.from(context)
            val dialogBuilder: AlertDialog.Builder?
            val dialog: AlertDialog?
            val dbHandler = TodoDatabaseHandler(context)

            val view = layoutInflater!!.inflate(R.layout.editpopup, null)
            val editTodoName = view.editNameId
            val editSaveButton = view.editSaveButtonId

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            editSaveButton.setOnClickListener {

                var newName = editTodoName.text.toString().trim()

                if(!TextUtils.isEmpty(newName)){

                    clone.todoName = newName

                    dbHandler.updateTodo(clone)
                    notifyItemChanged(adapterPosition, clone)

                    dialog!!.dismiss()

                }else{

                    Toast.makeText(context, "Enter new To-Do name...", Toast.LENGTH_LONG).show()

                }

            }

        }
    }
}