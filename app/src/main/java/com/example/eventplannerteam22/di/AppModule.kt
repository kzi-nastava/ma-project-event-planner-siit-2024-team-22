package com.example.eventplannerteam22.di

import android.content.Context
import android.content.SharedPreferences
import com.example.eventplannerteam22.auth.AuthApi
import com.example.eventplannerteam22.budgetPlan.data.BudgetPlanApiService
import com.example.eventplannerteam22.eventType.data.api.EventTypeApi
import com.example.eventplannerteam22.events.data.api.EventApi
import com.example.eventplannerteam22.priceList.data.PriceListApiService
import com.example.eventplannerteam22.products.BigDecimalAdapter
import com.example.eventplannerteam22.products.data.api.ProductApi
import com.example.eventplannerteam22.profile.data.ProfileApi
import com.example.eventplannerteam22.solutionCategory.data.SolutionCategoryApi
import com.example.eventplannerteam22.solutions.DurationAdapter
import com.example.eventplannerteam22.solutions.LocalDateAdapter
import com.example.eventplannerteam22.solutions.data.SolutionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(LocalDateAdapter())
            .add(DurationAdapter())
            .add(BigDecimalAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventApi(retrofit: Retrofit): EventApi {
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSolutionApi(retrofit: Retrofit): SolutionApi {
        return retrofit.create(SolutionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventTypeApi(retrofit: Retrofit): EventTypeApi {
        return retrofit.create(EventTypeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSolutionCategoryApi(retrofit: Retrofit): SolutionCategoryApi {
        return retrofit.create(SolutionCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBudgetPlanApi(retrofit: Retrofit): BudgetPlanApiService {
        return retrofit.create(BudgetPlanApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePriceListApi(retrofit: Retrofit): PriceListApiService {
        return retrofit.create(PriceListApiService::class.java)
    }
}