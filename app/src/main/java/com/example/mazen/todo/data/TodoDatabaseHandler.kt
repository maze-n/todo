package com.example.mazen.todo.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mazen.todo.model.*

class TodoDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_TODO_NAME + " TEXT ," + KEY_SHOWN_DATE + " TEXT ," + KEY_TODO_DATE + " LONG)"

        db!!.execSQL(CREATE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        onCreate(db)

    }

    fun createTodo(item: Todo){

        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()

        values.put(KEY_TODO_NAME, item.todoName)
        values.put(KEY_TODO_DATE, item.todoDate)
        values.put(KEY_SHOWN_DATE, item.formattedDate)

        db.insert(TABLE_NAME, null, values)

    }

    fun readTodo(): ArrayList<Todo> {

        val db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Todo> = ArrayList()

        val selectAll = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(selectAll, null)

        if(cursor.moveToFirst()){

            do{
                var item = Todo()

                item.todoId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                item.todoName = cursor.getString(cursor.getColumnIndex(KEY_TODO_NAME))
                item.formattedDate = cursor.getString(cursor.getColumnIndex(KEY_SHOWN_DATE))

                list.add(item)

            }while(cursor.moveToNext())
        }

        return list
    }

    fun updateTodo(item: Todo): Int{

        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()

        values.put(KEY_TODO_NAME, item.todoName)

        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(item.todoId.toString()))

    }

    fun deleteTodo(id: Int){

        val db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))

        db.close()

    }

    fun getTodoCount(): Int{

        val db: SQLiteDatabase = readableDatabase
        val countQuery = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count

    }

}