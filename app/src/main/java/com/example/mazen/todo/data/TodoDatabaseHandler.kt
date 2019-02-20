package com.example.mazen.todo.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mazen.todo.model.*

class TodoDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        var CREATE = "CREATE TABLE " + DATABASE_NAME + "(" + KEY_ID + " PRIMARY KEY ," + KEY_TODO_NAME + " TEXT)"

        db!!.execSQL(CREATE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        onCreate(db)

    }

}