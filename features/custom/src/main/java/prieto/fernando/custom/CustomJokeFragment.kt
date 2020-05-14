package prieto.fernando.custom

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import prieto.fernando.model.NamesData
import prieto.fernando.ui.BaseFragment
import prieto.fernando.ui.observe
import kotlinx.android.synthetic.main.fragment_custom_joke.button_done as doneButton
import kotlinx.android.synthetic.main.fragment_custom_joke.first_name_text as firstName
import kotlinx.android.synthetic.main.fragment_custom_joke.last_name_text as lastName

class CustomJokeFragment : BaseFragment<CustomJokeViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_custom_joke, container, false)!!

    override fun onResume() {
        super.onResume()
        setupInputListeners()
    }

    private val namesData: NamesData
        get() = NamesData(
            firstName.text.toString(),
            lastName.text.toString()
        )

    private val formTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.inputs.onNamesChanged(namesData)
        }
    }

    private fun setupInputListeners() {
        bindClickAction(doneButton) {
            viewModel.inputs.customRandomJoke(firstName.text.toString(), lastName.text.toString())
        }
        firstName.addTextChangedListener(formTextWatcher)
        lastName.addTextChangedListener(formTextWatcher)
    }

    override val viewModel: CustomJokeViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(CustomJokeViewModel::class.java).apply {
            observe(doneButtonEnabled(), ::changeDoneButtonState)
            observe(customRandomJokeRetrieved(), ::goBackToDashboard)
            observe(errorResource(), ::showErrorToast)
        }
    }

    private fun goBackToDashboard(unit: Unit?) {
        findNavController().popBackStack(R.id.dashboardFragment, false)
    }

    private fun changeDoneButtonState(enabled: Boolean?) {
        doneButton.isEnabled = enabled ?: false
    }
}
