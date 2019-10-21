package prieto.fernando.jokesapp.presentation.custom

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.jokesapp.presentation.setupViewModelForTests
import prieto.fernando.usecase.GetCustomRandomJokeUseCase

@RunWith(MockitoJUnitRunner::class)
class CustomJokeViewModelTest {
    private lateinit var cut: CustomJokeViewModel

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var customJokeUseCase: GetCustomRandomJokeUseCase

    @Mock
    lateinit var buttonStateEvaluator: NamesButtonStateEvaluator

    private lateinit var customRandomJokeRetrievedTestObserver: TestObserver<Unit>

    @Before
    fun setUp() {
        cut = CustomJokeViewModel(
            application,
            customJokeUseCase,
            buttonStateEvaluator
        )
        setupViewModelForTests(cut)

        customRandomJokeRetrievedTestObserver = cut.outputs.customRandomJokeRetrieved().test()
    }

    @Test
    fun `Given names when customRandomJoke then returns customRandomJokeRetrieved invoked`() {
        // Given
        val firstName = "firstName"
        val lastName = "lastName"
        val expected = mock<RandomJokeDomainModel>()

        whenever(
            customJokeUseCase.execute(firstName, lastName)
        ).thenReturn(
            Single.just(expected)
        )

        // When
        cut.customRandomJoke(firstName, lastName)

        // Then
        customRandomJokeRetrievedTestObserver.assertValue(Unit)
            .assertNoErrors()
    }

    @Test
    fun `Given NamesData when onNamesChanged then button enabled`() {
        // Given
        val namesData = NamesData("firstName", "lastName")
        val doneButtonEnabledTestObserver = cut.outputs.doneButtonEnabled().test()
        val expected = true

        whenever(
            buttonStateEvaluator.shouldEnableButton("firstName", "lastName")
        ).thenReturn(expected)

        // When
        cut.onNamesChanged(namesData)

        // Then
        doneButtonEnabledTestObserver.assertValueCount(2)
            .assertNoErrors()
    }
}
