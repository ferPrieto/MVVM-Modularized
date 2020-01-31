package prieto.fernando.custom

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.model.NamesData
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

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var customRandomJokeRetrievedTestObserver: Observer<Unit>
    private lateinit var doneButtonEnabledTestObserver: Observer<Boolean>

    @Before
    fun setUp() {
        cut = CustomJokeViewModel(
            customJokeUseCase,
            buttonStateEvaluator
        )
        setupViewModelForTests(cut)

        customRandomJokeRetrievedTestObserver = mock()
        doneButtonEnabledTestObserver = mock()
        cut.customRandomJokeRetrieved().observeForever(customRandomJokeRetrievedTestObserver)
        cut.doneButtonEnabled().observeForever(doneButtonEnabledTestObserver)
    }


    @Test
    fun `Given names when customRandomJoke then customRandomJokeRetrieved invoked with expected value`() {
        // Given
        val firstName = "firstName"
        val lastName = "lastName"

        given(customJokeUseCase.execute(firstName, lastName)).willReturn(
            Single.just(mock())
        )
        val expected = Unit

        // When
        cut.customRandomJoke(firstName, lastName)

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(customRandomJokeRetrievedTestObserver, times(1)).onChanged(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `Given NamesData when onNamesChanged then doneButtonEnabled invoked`() {
        // Given
        val namesData = NamesData("firstName", "lastName")
        val expected = false

        given(
            buttonStateEvaluator.shouldEnableButton("firstName", "lastName")
        ).willReturn(expected)

        // When
        cut.onNamesChanged(namesData)

        // Then
        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(doneButtonEnabledTestObserver, times(1)).onChanged(capture())
        }
    }
}
