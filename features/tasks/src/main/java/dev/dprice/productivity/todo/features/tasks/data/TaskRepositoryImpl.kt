package dev.dprice.productivity.todo.features.tasks.data

import dev.dprice.productivity.todo.features.tasks.model.TaskRepository

class TaskRepositoryImpl(private val taskService: TaskService) : TaskRepository