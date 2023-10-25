package com.ufms.nes.features.form.data.di
import com.ufms.nes.features.form.data.repository.FormRepository
import com.ufms.nes.features.form.data.repository.FormRepositoryImpl
import com.ufms.nes.features.models.data.repository.ModelRepository
import com.ufms.nes.features.models.data.repository.ModelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FormModule {

    @Singleton
    @Binds
    fun bindsFormRepository(
        formRepository: FormRepositoryImpl
    ): FormRepository
}
