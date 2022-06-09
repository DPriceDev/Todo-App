package dev.dprice.productivity.todo.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.platform.di.Default
import dev.dprice.productivity.todo.platform.di.IO
import dev.dprice.productivity.todo.platform.di.Ui
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Ui
    fun provideUiDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @IO
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Default
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
