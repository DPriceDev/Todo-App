package dev.dprice.productivity.todo.features.groups.dataimpl.di

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
import dev.dprice.productivity.todo.features.groups.GroupItem
import dev.dprice.productivity.todo.features.groups.GroupsQueries
import dev.dprice.productivity.todo.features.groups.data.GroupRepository
import dev.dprice.productivity.todo.features.groups.dataimpl.GroupRepositoryImpl
import dev.dprice.productivity.todo.features.groups.dataimpl.GroupsDatabase
import dev.dprice.productivity.todo.features.groups.dataimpl.converters.ColourAdapter
import dev.dprice.productivity.todo.features.groups.dataimpl.converters.IconAdapter
import dev.dprice.productivity.todo.platform.di.Default
import dev.dprice.productivity.todo.platform.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GroupDataModule {

    @Provides
    @Singleton
    @Named("Group")
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(GroupsDatabase.Schema, context, "groups.db")
    }

    @Provides
    @Singleton
    fun provideGroupDatabase(@Named("Group") driver: SqlDriver): GroupsDatabase = GroupsDatabase(
        driver,
        GroupItem.Adapter(
            iconAdapter = IconAdapter(),
            colourAdapter = ColourAdapter()
        )
    )

    @Provides
    @Singleton
    fun provideGroupsQueries(database: GroupsDatabase) = database.groupsQueries

    @Provides
    @Singleton
    @Named("GroupsDataStore")
    fun provideGroupDataStore(
    @ApplicationContext context: Context,
    @Default defaultDispatcher: CoroutineDispatcher
    ) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(defaultDispatcher)
        ) {
            context.preferencesDataStoreFile("GroupsDataStore")
        }
    }

    @Provides
    @Singleton
    fun provideGroupRepository(
    @Named("GroupsDataStore") datastore: DataStore<Preferences>,
    @IO ioDispatcher: CoroutineDispatcher,
    queries: GroupsQueries
    ) : GroupRepository {
        return GroupRepositoryImpl(datastore, ioDispatcher, queries)
    }
}