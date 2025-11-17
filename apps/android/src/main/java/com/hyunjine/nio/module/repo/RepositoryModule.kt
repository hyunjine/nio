package com.hyunjine.nio.module.repo

import com.hyunjine.clothes.ClothesRepository
import com.hyunjine.lock.LockRepository
import com.hyunjine.nio.clothes.ClothesRepositoryImpl
import com.hyunjine.nio.lock.LockRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindClothesRepository(impl: ClothesRepositoryImpl): ClothesRepository

    @Binds
    @Singleton
    abstract fun bindLockRepository(impl: LockRepositoryImpl): LockRepository
}