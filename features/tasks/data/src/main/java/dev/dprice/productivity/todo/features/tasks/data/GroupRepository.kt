package dev.dprice.productivity.todo.features.tasks.data

import dev.dprice.productivity.todo.features.tasks.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroups() : Flow<List<Group>>

    suspend fun setCurrentGroup(id: String?)
    fun getCurrentGroup() : Flow<Group?>

    fun addGroup(group: Group)
    fun updateGroup(group: Group)
    fun deleteGroup(id: String)
}