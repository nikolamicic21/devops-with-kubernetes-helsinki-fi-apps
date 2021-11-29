package io.nikolamicic21.simpletodoapp.broadcaster.event

data class Todo(val id: Long, val title: String, val status: TodoStatus)

enum class TodoStatus {
    NEW, IN_PROGRESS, DONE
}
