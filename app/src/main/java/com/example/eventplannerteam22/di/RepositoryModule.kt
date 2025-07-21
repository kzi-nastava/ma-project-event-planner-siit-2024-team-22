package com.example.eventplannerteam22.di

import com.example.eventplannerteam22.auth.AuthRepository
import com.example.eventplannerteam22.auth.AuthRepositoryImpl
import com.example.eventplannerteam22.eventType.EventTypeRepository
import com.example.eventplannerteam22.eventType.EventTypeRepositoryImpl
import com.example.eventplannerteam22.profile.ProfileRepository
import com.example.eventplannerteam22.profile.ProfileRepositoryImpl
import com.example.eventplannerteam22.session.SessionRepository
import com.example.eventplannerteam22.session.SessionRepositorySharedPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositorySharedPreferencesImpl
    ): SessionRepository

    @Binds
    abstract fun bindEvetTypeRepository(
        impl: EventTypeRepositoryImpl
    ): EventTypeRepository
}