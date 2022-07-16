package dev.dprice.productivity.todo.features.groups.data

import dev.dprice.productivity.todo.features.groups.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroups() : Flow<List<Group>>

    suspend fun setCurrentGroup(id: String?)
    fun getCurrentGroup() : Flow<Group?>

    suspend fun addGroup(group: Group)
    suspend fun updateGroup(group: Group)

    suspend fun updateGroupsDeleted(isDeleted: Boolean, ids: List<String>)

    suspend fun deleteGroups(ids: List<String>)
}