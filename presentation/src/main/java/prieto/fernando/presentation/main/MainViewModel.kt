package prieto.fernando.presentation.main

import android.app.Application
import javax.inject.Inject
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import prieto.fernando.presentation.BaseViewModelOutputs

interface MainViewModelInputs : BaseViewModelInputs

interface MainViewModelOutputs : BaseViewModelOutputs

open class MainViewModel @Inject constructor(application: Application) : BaseViewModel(application),
    MainViewModelInputs,
    MainViewModelOutputs {

    override val inputs: MainViewModelInputs
        get() = this

    override val outputs: MainViewModelOutputs
        get() = this
}
