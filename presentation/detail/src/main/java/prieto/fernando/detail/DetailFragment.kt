package prieto.fernando.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_detail.*
import prieto.fernando.ui.BaseFragment

class DetailFragment : BaseFragment<DetailViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_detail, container, false)!!

    override fun onResume() {
        super.onResume()
        getBundledContents()
    }

    private fun getBundledContents() {
        arguments?.let { bundle ->
            val joke = bundle.getString(DETAIL_FRAGMENT_TYPE_ARG)
                ?: throw IllegalStateException("Joke should be provided")
            selected_joke.text = joke
        }
    }

    override val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(DetailViewModel::class.java)
    }

    companion object {
        const val DETAIL_FRAGMENT_TYPE_ARG = "joke"
    }
}