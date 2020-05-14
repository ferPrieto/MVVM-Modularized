package prieto.fernando.main.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import prieto.fernando.main.R
import prieto.fernando.main.vm.MainViewModel
import prieto.fernando.ui.BaseActivity
import javax.inject.Inject

class MainActivity @Inject constructor() : BaseActivity<MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.mainNavigationFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp()

    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
    }
}
