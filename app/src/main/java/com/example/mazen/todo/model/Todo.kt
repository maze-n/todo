package com.example.mazen.todo.model

import java.text.DateFormat
import java.util.*

class Todo(){

    var todoId: Int? = null
    var todoName: String? = null
    var todoDate: Long? = null
    var formattedDate: String? = null

    constructor(id: Int, name: String, date: Long, realDate: String): this(){

        this.todoId = id
        this.todoName = name
        this.todoDate = date
        this.formattedDate = realDate
    }

    fun showHumanDate(date: Long): String{

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate: String = dateFormat.format(Date(date).time)

        return formattedDate

    }

}