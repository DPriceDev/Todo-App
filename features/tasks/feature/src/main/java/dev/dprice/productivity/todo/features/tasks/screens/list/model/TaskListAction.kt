package dev.dprice.productivity.todo.features.tasks.screens.list.model

import androidx.annotation.StringRes
import dev.dprice.productivity.todo.features.tasks.R
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.features.tasks.data.model.Task

enum class TaskFilter(@StringRes val displayNameId: Int) {
    ALL(R.string.filter_all),
    INCOMPLETE(R.string.filter_incomplete),
    COMPLETE(R.string.filter_complete)
}

enum class DateFilter(@StringRes val displayNameId: Int) {
    ALL(R.string.filter_all),
    DAILY(R.string.filter_daily),
    THREE_DAY(R.string.filter_three_day),
    WEEKLY(R.string.filter_weekly),
    MONTHLY(R.string.filter_monthly)
}

sealed class TaskListAction {
    data class UpdateTasks(val tasks: List<Task>) : TaskListAction()
    data class SelectTask(val id: String) : TaskListAction()
    data class CompleteTask(val id: String) : TaskListAction()
    data class DeleteTask(val id: String) : TaskListAction()

    object SearchButtonClicked : TaskListAction()

    data class UpdateSearchText(val value: String) : TaskListAction()
    data class UpdateSearchFocus(val focus: Boolean) : TaskListAction()

    data class UpdateFilter(val filter: TaskFilter) : TaskListAction()
    data class UpdateGroup(val group: Group?) : TaskListAction()
}
