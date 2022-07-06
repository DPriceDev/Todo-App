package dev.dprice.productivity.todo.features.tasks.dataimpl.groups

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher
): GroupRepository {

    override fun getGroups(): Flow<List<Group>> = flow {
        emit(
            listOf(
                Group(
                    "sdfsd",
                    "Work"
                ),
                Group(
                    "sdfsdfs",
                    "Wedding"
                )
            )
        )
    }

    override fun getCurrentGroup(): Flow<Group?> = datastore
        .data
        .map { it[CURRENT_GROUP_KEY] }
        .combine(getGroups()) { id, groups ->
            Timber.tag("Davids Log").e("group!")
            groups.find { it.id == id }
        }

    override suspend fun setCurrentGroup(id: String?) {
        withContext(ioDispatcher) {
            datastore.edit { preferences ->
                id?.let { preferences[CURRENT_GROUP_KEY] == it }
                    ?: preferences.remove(CURRENT_GROUP_KEY)
            }
        }
    }

    override fun addGroup(group: Group) {
        TODO("Not yet implemented")
    }

    override fun updateGroup(group: Group) {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(id: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private val CURRENT_GROUP_KEY = stringPreferencesKey("groups_current_group")
    }
}