package dev.dprice.productivity.todo.auth.feature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.feature.sdk.MockAuthenticationSource

@Module
@InstallIn(SingletonComponent::class)
abstract class MockAuthModule {

    @Binds
    abstract fun MockAuthenticationSource.bindMockAuthenticationSource(): AuthenticationSource
}
