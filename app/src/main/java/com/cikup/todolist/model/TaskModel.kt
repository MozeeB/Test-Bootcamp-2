package com.cikup.todolist.model

import com.google.firebase.Timestamp
import java.util.*

data class TaskModel(
    val id:String = "",
    val title:String = "",
    val description:String = "",
    val status:Boolean = false,
    val dateTime: Timestamp = Timestamp(Date())
)