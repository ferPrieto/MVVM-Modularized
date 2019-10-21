package prieto.fernando.jokesapp.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.main.MainViewModel
import prieto.fernando.ui.BaseActivity

class MainActivity : prieto.fernando.ui.BaseActivity<MainViewModel>() {
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
