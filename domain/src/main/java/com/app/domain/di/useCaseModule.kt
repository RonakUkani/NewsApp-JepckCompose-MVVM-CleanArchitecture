package com.app.domain.di

import com.app.domain.mapper.NewsMapper
import com.app.domain.usecase.NewsUseCase
import com.app.domain.usecase.NewsUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::NewsUseCaseImpl) { bind<NewsUseCase>() }
    single { NewsMapper() }
}
