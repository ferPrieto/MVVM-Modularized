package domain.repository

import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.JokesRemoteSource
import prieto.fernando.mapper.MultipleRandomJokeResponseToLocalModelMapper
import prieto.fernando.mapper.RandomJokeResponseToLocalModelMapper
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.JokeResponse
import prieto.fernando.model.RandomJokeLocalModel
import prieto.fernando.model.RandomJokeResponse
import prieto.fernando.repository.JokesRepositoryImpl
import prieto.fernando.source.JokesLocalSource

@RunWith(MockitoJUnitRunner::class)
class JokesRepositoryImplTest {
    private lateinit var cut: JokesRepositoryImpl

    @Mock
    lateinit var localSource: JokesLocalSource

    @Mock
    lateinit var remoteSource: JokesRemoteSource

    @Mock
    lateinit var jokeResponseToLocalMapper: RandomJokeResponseToLocalModelMapper

    @Mock
    lateinit var multipleJokesResponseToLocalMapper: MultipleRandomJokeResponseToLocalModelMapper

    @Before
    fun setUp() {
        cut = JokesRepositoryImpl(
            localSource,
            remoteSource,
            jokeResponseToLocalMapper,
            multipleJokesResponseToLocalMapper
        )
    }

    @Test
    fun `When randomJoke then return RandomJokeLocalModel`() {
        // Given
        val randomJokeResponse = RandomJokeResponse(
            "some Type",
            JokeResponse(
                "some Id",
                "nice joke",
                listOf("explicit")
            )
        )

        val randomJokeLocalModel = RandomJokeLocalModel(
            "some Id",
            "nice joke",
            listOf(CategoryLocalModel.EXPLICIT)
        )

        given { remoteSource.getRandomJoke() }.willReturn(
            Single.just(randomJokeResponse)
        )
        given { jokeResponseToLocalMapper.toLocal(randomJokeResponse) }.willReturn(
            randomJokeLocalModel
        )

        // When
        val actualValue = cut.randomJoke()

        // Then
        actualValue
            .test()
            .assertValue(randomJokeLocalModel)
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `Given input parameters when randomCustomJoke then return RandomJokeLocalModel`() {
        // Given
        val randomJokeResponse = RandomJokeResponse(
            "some Type",
            JokeResponse(
                "some Id",
                "nice joke mentioning firstName lastName",
                listOf("explicit")
            )
        )

        val randomJokeLocalModel = RandomJokeLocalModel(
            "some Id",
            "nice joke mentioning firstName lastName",
            listOf(CategoryLocalModel.EXPLICIT)
        )
        given { remoteSource.getRandomCustomJoke("firstName", "lastName") }.willReturn(
            Single.just(randomJokeResponse)
        )
        given { jokeResponseToLocalMapper.toLocal(randomJokeResponse) }.willReturn(
            randomJokeLocalModel
        )

        // When
        val actualValue = cut.randomCustomJoke("firstName", "lastName")

        // Then
        actualValue
            .test()
            .assertValue(randomJokeLocalModel)
            .assertNoErrors()
            .assertComplete()
    }
}
