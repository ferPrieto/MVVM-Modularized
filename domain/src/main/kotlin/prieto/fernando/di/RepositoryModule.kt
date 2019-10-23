package di

import dagger.Module
import dagger.Provides
import prieto.fernando.data.JokesRemoteSource
import prieto.fernando.mapper.MultipleRandomJokeResponseToLocalModelMapper
import prieto.fernando.mapper.RandomJokeResponseToLocalModelMapper
import prieto.fernando.repository.JokesRepositoryImpl
import prieto.fernando.source.JokesLocalSource

@Module
class RepositoryModule {

    @Module
    companion object {
        @Provides
        fun provideRandomJokeResponseToLocalModelMapperRepository(
            localSource: JokesLocalSource,
            remoteSource: JokesRemoteSource,
            jokeResponseToLocalMapper: RandomJokeResponseToLocalModelMapper,
            multipleJokesResponseToLocalMapper: MultipleRandomJokeResponseToLocalModelMapper
        ) = JokesRepositoryImpl(
            localSource,
            remoteSource,
            jokeResponseToLocalMapper,
            multipleJokesResponseToLocalMapper
        )
    }
}
