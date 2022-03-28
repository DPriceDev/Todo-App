package dev.dprice.productivity.todo.auth.landing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.landing.navigation.LandingAuthNavigationComponent
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthLandingModule {

    @IntoSet
    @Binds
    abstract fun LandingAuthNavigationComponent.bindLandingAuthNavigationComponent() : AuthNavigationComponent
}