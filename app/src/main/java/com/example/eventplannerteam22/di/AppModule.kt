
package com.example.eventplannerteam22.di

import com.example.eventplannerteam22.userreport.presentation.UserReportApi
import com.example.eventplannerteam22.userreport.presentation.UserReportRepository

import com.example.eventplannerteam22.blockedusers.data.BlockedUsersApi
import com.example.eventplannerteam22.blockedusers.data.BlockedUsersRepositoryImpl
import com.example.eventplannerteam22.blockedusers.domain.BlockedUsersRepository

import android.content.Context
import android.content.SharedPreferences
import com.example.eventplannerteam22.admin.comments.data.api.AdminCommentApi
import com.example.eventplannerteam22.auth.AuthApi
import com.example.eventplannerteam22.budgetPlan.data.BudgetPlanApiService
import com.example.eventplannerteam22.chat.data.ChatApi
import com.example.eventplannerteam22.chat.data.ChatRepository
import com.example.eventplannerteam22.chat.data.ChatWebSocketService
import com.example.eventplannerteam22.chat.utils.UUIDAdapter
import com.example.eventplannerteam22.eventactivity.data.model.LocalTimeAdapter
import com.example.eventplannerteam22.events.comments.data.EventCommentApi
import com.example.eventplannerteam22.events.data.api.EventApi
import com.example.eventplannerteam22.events.invite.data.EventInviteApi
import com.example.eventplannerteam22.eventtype.data.api.EventTypeApi
import com.example.eventplannerteam22.favorites.data.api.FavoriteEventApi
import com.example.eventplannerteam22.favorites.data.api.FavoriteProductApi
import com.example.eventplannerteam22.favorites.data.api.FavoriteSolutionApi
import com.example.eventplannerteam22.favorites.data.repository.FavoriteRepository
import com.example.eventplannerteam22.notifications.data.api.NotificationApi
import com.example.eventplannerteam22.priceList.data.PriceListApiService
import com.example.eventplannerteam22.productcategory.data.ProductCategoryApi
import com.example.eventplannerteam22.products.BigDecimalAdapter
import com.example.eventplannerteam22.products.comments.data.ProductCommentApi
import com.example.eventplannerteam22.products.data.api.ProductApi
import com.example.eventplannerteam22.profile.data.ProfileApi
import com.example.eventplannerteam22.session.SessionRepository
import com.example.eventplannerteam22.solutionCategory.data.SolutionCategoryApi
import com.example.eventplannerteam22.solutions.DurationAdapter
import com.example.eventplannerteam22.solutions.LocalDateAdapter
import com.example.eventplannerteam22.solutions.comments.data.SolutionCommentApi
import com.example.eventplannerteam22.solutions.data.SolutionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

object UnauthenticatedPaths {
    private val paths = setOf(
        "auth/login",
        "auth/register",
        "auth/refresh",
    )

    fun isUnauthenticatedPath(path: String): Boolean {
        return paths.any { unauthPath ->
            path.contains(unauthPath, ignoreCase = true)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserReportApi(retrofit: Retrofit): UserReportApi {
        return retrofit.create(UserReportApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserReportRepository(api: UserReportApi): UserReportRepository {
        return UserReportRepository(api)
    }

    @Provides
    @Singleton
    fun provideBlockedUsersApi(retrofit: Retrofit): BlockedUsersApi {
        return retrofit.create(BlockedUsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBlockedUsersRepository(api: BlockedUsersApi): BlockedUsersRepository {
        return BlockedUsersRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        sessionRepository: SessionRepository
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                if (UnauthenticatedPaths.isUnauthenticatedPath(request.url.encodedPath)) {
                    chain.proceed(request)
                } else {
                    val token = sessionRepository.getAccessToken()
                    chain.proceed(
                        request.newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    )
                }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(com.example.eventplannerteam22.notifications.data.LocalDateTimeAdapter())
            .add(LocalDateAdapter())
            .add(DurationAdapter())
            .add(BigDecimalAdapter())
            .add(LocalTimeAdapter())
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
    fun provideProductCommentApi(retrofit: Retrofit): ProductCommentApi {
        return retrofit.create(ProductCommentApi::class.java)
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
    fun provideAdminComments(retrofit: Retrofit): AdminCommentApi {
        return retrofit.create(AdminCommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSolutionCategoryApi(retrofit: Retrofit): SolutionCategoryApi {
        return retrofit.create(SolutionCategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductCategoryApi(retrofit: Retrofit): ProductCategoryApi {
        return retrofit.create(ProductCategoryApi::class.java)
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

    @Provides
    @Singleton
    fun provideSolutionCommentApi(retrofit: Retrofit): SolutionCommentApi {
        return retrofit.create(SolutionCommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit): NotificationApi {
        return retrofit.create(NotificationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventCommentApi(retrofit: Retrofit): EventCommentApi {
        return retrofit.create(EventCommentApi::class.java)
    }
    @Provides
    fun provideEventInviteApi(retrofit: Retrofit): EventInviteApi {
        return retrofit.create(EventInviteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteEventApi(retrofit: Retrofit): FavoriteEventApi =
        retrofit.create(FavoriteEventApi::class.java)

    @Provides
    @Singleton
    fun provideFavoriteProductApi(retrofit: Retrofit): FavoriteProductApi =
        retrofit.create(FavoriteProductApi::class.java)

    @Provides
    @Singleton
    fun provideFavoriteSolutionApi(retrofit: Retrofit): FavoriteSolutionApi =
        retrofit.create(FavoriteSolutionApi::class.java)

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        eventApi: FavoriteEventApi,
        productApi: FavoriteProductApi,
        solutionApi: FavoriteSolutionApi
    ): FavoriteRepository = FavoriteRepository(eventApi, productApi, solutionApi)

    @Provides
    @Singleton
    fun provideChatWebSocketService(
        sessionRepository: SessionRepository,
        @ApplicationContext context: Context,
        moshi: Moshi
    ): ChatWebSocketService {
        return ChatWebSocketService(sessionRepository, context, moshi)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        chatWebSocketService: ChatWebSocketService,
        chatApi: ChatApi,
        sessionRepository: SessionRepository
    ): ChatRepository {
        return ChatRepository(chatWebSocketService, chatApi, sessionRepository)
    }

    @Provides
    @Singleton
    fun provideChatApi(retrofit: Retrofit): ChatApi {
        return retrofit.create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(UUIDAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}