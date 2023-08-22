

package com.ufms.nes.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.ufms.nes.data.AgemsTypeRepository
import com.ufms.nes.features.authentication.data.di.DataModule
import com.ufms.nes.data.di.FakeAgemsTypeRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeAgemsTypeRepository
    ): AgemsTypeRepository
}
