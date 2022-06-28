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
import dev.dprice.productivity.todo.features.tasks.TaskItem
import dev.dprice.productivity.todo.features.tasks.TasksQueries
import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.dataimpl.TaskRepositoryImpl
import dev.dprice.productivity.todo.features.tasks.dataimpl.TasksDatabase
import dev.dprice.productivity.todo.features.tasks.dataimpl.converters.LocalDateTimeAdapter
import dev.dprice.productivity.todo.features.tasks.dataimpl.groups.GroupRepositoryImpl
import dev.dprice.productivity.todo.platform.di.Default
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
        TaskItem.Adapter(dateAdapter = LocalDateTimeAdapter())
    )

    @Provides
    @Singleton
    fun provideTasksQueries(database: TasksDatabase) = database.tasksQueries

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
        @Named("TasksDataStore") datastore: DataStore<Preferences>
    ) : GroupRepository {
        return GroupRepositoryImpl(datastore)
    }
}