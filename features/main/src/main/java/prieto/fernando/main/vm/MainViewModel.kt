package prieto.fernando.main.vm

import prieto.fernando.vm.BaseViewModel
import prieto.fernando.vm.BaseViewModelInputs
import javax.inject.Inject

interface MainViewModelInputs : BaseViewModelInputs

open class MainViewModel @Inject constructor() : BaseViewModel(),
    MainViewModelInputs {

    override val inputs: MainViewModelInputs
        get() = this
}
