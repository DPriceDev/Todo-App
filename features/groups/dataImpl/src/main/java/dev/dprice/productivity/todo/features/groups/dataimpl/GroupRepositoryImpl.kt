package dev.dprice.productivity.todo.features.groups.dataimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.dprice.productivity.todo.features.groups.GroupItem
import dev.dprice.productivity.todo.features.groups.GroupsQueries
import dev.dprice.productivity.todo.features.groups.data.GroupRepository
import dev.dprice.productivity.todo.features.groups.data.model.Group
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher,
    private val queries: GroupsQueries
): GroupRepository {

    override fun getGroups(): Flow<List<Group>> = queries.selectAll()
        .asFlow()
        .mapToList()
        .map { groupList ->
            groupList.map { it.toGroup() }
        }

    override fun getCurrentGroup(): Flow<Group?> = datastore
        .data
        .map { it[CURRENT_GROUP_KEY] }
        .combine(getGroups()) { id, groups -> groups.find { it.id == id } }

    override suspend fun setCurrentGroup(id: String?) {
        withContext(ioDispatcher) {
            datastore.edit { preferences ->
                id?.let {
                    preferences.putAll(CURRENT_GROUP_KEY to it)
                } ?: preferences.remove(CURRENT_GROUP_KEY)
            }
        }
    }

    override suspend fun addGroup(group: Group) {
        queries.insert(
            group.id,
            group.name,
            group.colour,
            group.icon,
            group.isDeleted
        )
        // todo: add to server
    }

    override suspend fun updateGroup(group: Group) {
        queries.insert(
            group.id,
            group.name,
            group.colour,
            group.icon,
            group.isDeleted
        )
    }

    override suspend fun updateGroupsDeleted(isDeleted: Boolean, ids: List<String>) {
        queries.transaction {
            ids.forEach { id -> queries.updateIsDeleted(isDeleted, id) }
        }
    }

    override suspend fun deleteGroups(ids: List<String>) {
        queries.transaction {
            ids.forEach { queries.delete(it) }
        }
    }

    companion object {
        private val CURRENT_GROUP_KEY = stringPreferencesKey("groups_current_group")
    }
}

fun GroupItem.toGroup() = Group(
    id = id,
    name = title,
    colour = colour,
    icon = icon,
    isDeleted = isDeleted
)