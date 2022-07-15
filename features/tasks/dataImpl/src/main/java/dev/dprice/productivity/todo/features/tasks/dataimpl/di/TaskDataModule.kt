package dev.dprice.productivity.todo.features.tasks.dataimpl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.GroupItem
import dev.dprice.productivity.todo.features.tasks.GroupsQueries
import dev.dprice.productivity.todo.features.tasks.TaskItem
import dev.dprice.productivity.todo.features.tasks.TasksQueries
import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.dataimpl.TaskRepositoryImpl
import dev.dprice.productivity.todo.features.tasks.dataimpl.TasksDatabase
import dev.dprice.productivity.todo.features.tasks.dataimpl.converters.ColourAdapter
import dev.dprice.productivity.todo.features.tasks.dataimpl.converters.IconAdapter
import dev.dprice.productivity.todo.features.tasks.dataimpl.converters.LocalDateTimeAdapter
import dev.dprice.productivity.todo.features.tasks.dataimpl.groups.GroupRepositoryImpl
import dev.dprice.productivity.todo.platform.di.Default
import dev.dprice.productivity.todo.platform.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskDataModule {

    @Provides
    @Singleton
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(TasksDatabase.Schema, context, "tasks.db")
    }

    @Provides
    @Singleton
    fun provideTaskRepository(queries: TasksQueries) : TaskRepository {
        return TaskRepositoryImpl(queries)
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(driver: SqlDriver): TasksDatabase = TasksDatabase(
        driver,
        GroupItem.Adapter(
            iconAdapter = IconAdapter(),
            colourAdapter = ColourAdapter()
        ),
        TaskItem.Adapter(dateAdapter = LocalDateTimeAdapter())
    )

    @Provides
    @Singleton
    fun provideTasksQueries(database: TasksDatabase) = database.tasksQueries

    @Provides
    @Singleton
    fun provideGroupsQueries(database: TasksDatabase) = database.groupsQueries

    @Provides
    @Singleton
    @Named("TasksDataStore")
    fun provideGroupDataStore(
        @ApplicationContext context: Context,
        @Default defaultDispatcher: CoroutineDispatcher
    ) : DataStore<Preferences> {
         return PreferenceDataStoreFactory.create(
             scope = CoroutineScope(defaultDispatcher)
         ) {
             context.preferencesDataStoreFile("TasksDataStore")
         }
    }

    @Provides
    @Singleton
    fun provideGroupRepository(
        @Named("TasksDataStore") datastore: DataStore<Preferences>,
        @IO ioDispatcher: CoroutineDispatcher,
        queries: GroupsQueries
    ) : GroupRepository {
        return GroupRepositoryImpl(datastore, ioDispatcher, queries)
    }
}