package prieto.fernando.infinite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_joke.view.*
import prieto.fernando.infinite.R
import prieto.fernando.model.RandomJokeUiModel

interface BindableAdapter<T> {
    fun setData(data: T)
}

interface ClickListener {
    fun onItemClicked(joke: String)
}

class JokesAdapter (private val clickListener: ClickListener): RecyclerView.Adapter<JokesAdapter.JokeHolder>(),
    BindableAdapter<List<RandomJokeUiModel>> {
    private val jokes = mutableListOf<prieto.fernando.model.RandomJokeUiModel>()

    override fun setData(data: List<prieto.fernando.model.RandomJokeUiModel>) {
        jokes.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
        val inflater = LayoutInflater.from(parent.context)
        return JokeHolder(
            inflater.inflate(
                R.layout.item_joke,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = jokes.size

    override fun onBindViewHolder(holder: JokeHolder, position: Int) {
        holder.bind(jokes[position], clickListener)
    }

    class JokeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(randomJokeUiModel: prieto.fernando.model.RandomJokeUiModel, clickListener: ClickListener) {
            itemView.item_joke.text = randomJokeUiModel.joke
            itemView.setOnClickListener {
                clickListener.onItemClicked(randomJokeUiModel.joke)
            }
        }
    }
}
