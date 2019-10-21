package prieto.fernando.jokesapp.view.infinite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_joke.view.*
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.data.RandomJokeUiModel

interface BindableAdapter<T> {
    fun setData(data: T)
}

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.JokeHolder>(),
    BindableAdapter<List<RandomJokeUiModel>> {
    private var jokes = mutableListOf<RandomJokeUiModel>()

    override fun setData(data: List<RandomJokeUiModel>) {
        jokes.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
        val inflater = LayoutInflater.from(parent.context)
        return JokeHolder(inflater.inflate(R.layout.item_joke, parent, false))
    }

    override fun getItemCount() = jokes.size

    override fun onBindViewHolder(holder: JokeHolder, position: Int) {
        holder.bind(jokes[position])
    }

    class JokeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(randomJokeUiModel: RandomJokeUiModel) {
            itemView.item_joke.text = randomJokeUiModel.joke
        }
    }
}
