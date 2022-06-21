package dev.dprice.productivity.todo.features.tasks.di

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.Database
import dev.dprice.productivity.todo.features.tasks.Task
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.TaskRepositoryImpl
import dev.dprice.productivity.todo.features.tasks.data.TaskService
import dev.dprice.productivity.todo.features.tasks.data.TaskServiceImpl
import dev.dprice.productivity.todo.features.tasks.usecase.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TaskModule {

    @Binds
    fun TaskRepositoryImpl.bindTaskRepository() : TaskRepository

    @Binds
    fun AddTaskUseCaseImpl.bindAddTaskUseCase() : AddTaskUseCase

    @Binds
    fun GetTaskListUseCaseImpl.bindGetTaskListUseCase() : GetTaskListUseCase

    @Binds
    fun DeleteTaskUseCaseImpl.bindDeleteTaskUseCase() : DeleteTaskUseCase

    @Binds
    fun UpdateTaskUseCaseImpl.bindUpdateTaskUseCase() : UpdateTaskUseCase

    @Binds
    fun TaskServiceImpl.bindTaskService() : TaskService

    @Binds
    @Singleton
    fun UpdateTaskTitleEntryUseCaseImpl.bindUpdateTaskTitleEntryUseCase(): UpdateTaskTitleEntryUseCase

    @Binds
    @Singleton
    fun UpdateTaskDetailsEntryUseCaseImpl.bindUpdateTaskDetailsEntryUseCase(): UpdateTaskDetailsEntryUseCase

    // todo: task api
}

@Module
@InstallIn(SingletonComponent::class)
object TaskDataModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(driver: SqlDriver): Database = Database(
        driver,
        Task.Adapter(
            dateAdapter = object: ColumnAdapter<LocalDateTime, String> {
                override fun decode(databaseValue: String): LocalDateTime {
                    return Json.decodeFromString(LocalDateTime.serializer(), databaseValue)
                }

                override fun encode(value: LocalDateTime): String {
                    return Json.encodeToString(LocalDateTime.serializer(), value)
                }
            }
        )
    )

    @Provides
    @Singleton
    fun provideTasksQueries(database: Database) = database.tasksQueries
}