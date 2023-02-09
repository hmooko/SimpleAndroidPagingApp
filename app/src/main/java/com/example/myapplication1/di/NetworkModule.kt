package com.example.myapplication1.di

import com.example.myapplication1.datasource.BookRemoteDataSource
import com.example.myapplication1.datasource.MainPagingSource
import com.example.myapplication1.network.BookNetworkService
import com.example.myapplication1.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun providesBookNetworkService(): BookNetworkService =
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookNetworkService::class.java)

    @Singleton
    @Provides
    fun providesBookRemoteDataSource(bookNetworkService: BookNetworkService) =
        BookRemoteDataSource(bookNetworkService, Dispatchers.IO)

    /*@Singleton
    @Provides
    fun providesBookRepository(bookRemoteDataSource: BookRemoteDataSource) =
        BookRepository(bookRemoteDataSource)*/

    @Singleton
    @Provides
    fun providesBookRepository(bookNetworkService: BookNetworkService) =
        BookRepository(bookNetworkService)
}