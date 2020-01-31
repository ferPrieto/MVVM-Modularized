package prieto.fernando.presentation.main

import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import javax.inject.Inject

interface MainViewModelInputs : BaseViewModelInputs

open class MainViewModel @Inject constructor() : BaseViewModel(),
    MainViewModelInputs {

    override val inputs: MainViewModelInputs
        get() = this
}
