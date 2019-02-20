package com.example.mazen.todo.model

class Todo(){

    var todoId: Int? = null
    var todoName: String? = null

    constructor(id: Int, name: String): this(){

        this.todoId = id
        this.todoName = name
    }

}