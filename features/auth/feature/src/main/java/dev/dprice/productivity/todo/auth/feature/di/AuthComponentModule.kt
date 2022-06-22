package dev.dprice.productivity.todo.auth.feature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.feature.components.signout.SignOutComponent
import dev.dprice.productivity.todo.platform.model.Component
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
interface AuthComponentModule {

    @Binds
    @IntoSet
    @Named("Settings")
    fun SignOutComponent.bindSignOutComponent() : Component
}
