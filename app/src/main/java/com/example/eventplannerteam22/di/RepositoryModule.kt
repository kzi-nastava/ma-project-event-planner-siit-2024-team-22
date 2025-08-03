package com.example.eventplannerteam22.di

import com.example.eventplannerteam22.auth.AuthRepository
import com.example.eventplannerteam22.auth.AuthRepositoryImpl
import com.example.eventplannerteam22.eventtype.data.repository.EventTypeRepository
import com.example.eventplannerteam22.eventtype.data.repository.EventTypeRepositoryImpl
import com.example.eventplannerteam22.budgetPlan.data.BudgetPlanRepository
import com.example.eventplannerteam22.budgetPlan.data.BudgetPlanRepositoryImpl
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.events.data.repository.EventRepositoryImpl
import com.example.eventplannerteam22.products.data.repository.ProductRepository
import com.example.eventplannerteam22.products.data.repository.ProductRepositoryImpl
import com.example.eventplannerteam22.profile.data.ProfileRepository
import com.example.eventplannerteam22.profile.data.ProfileRepositoryImpl
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
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindSessionRepository(impl: SessionRepositorySharedPreferencesImpl): SessionRepository

    @Binds
    abstract fun bindEvetTypeRepository(impl: EventTypeRepositoryImpl): EventTypeRepository

    @Binds
    abstract fun bindEventRepository(impl: EventRepositoryImpl): EventRepository

    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindBudgetPlanRepository(impl: BudgetPlanRepositoryImpl): BudgetPlanRepository
}