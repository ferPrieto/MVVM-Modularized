package prieto.fernando.jokesapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import prieto.fernando.jokesapp.R
import prieto.fernando.ui.BaseFragment

class DetailFragment : BaseFragment<DetailViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_detail, container, false)!!

    override val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(DetailViewModel::class.java)
    }
}