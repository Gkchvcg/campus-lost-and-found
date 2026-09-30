package com.example.campuslostfound.di

import android.app.Application
import androidx.room.Room
import com.example.campuslostfound.data.local.database.AppDatabase
import com.example.campuslostfound.data.repository.ItemRepositoryImpl
import com.example.campuslostfound.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemRepository(db: AppDatabase): ItemRepository {
        return ItemRepositoryImpl(db.itemDao)
    }
}
