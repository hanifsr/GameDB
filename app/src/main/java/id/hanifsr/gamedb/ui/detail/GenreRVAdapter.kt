package id.hanifsr.gamedb.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Genre
import id.hanifsr.gamedb.databinding.ItemGenreBinding

class GenreRVAdapter(
	private var genre: List<Genre>
) : RecyclerView.Adapter<GenreRVAdapter.GenreRVHolder>() {

	fun updateGenre(genre: List<Genre>) {
		this.genre = genre
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreRVHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_genre, parent, false)
		return GenreRVHolder(view)
	}

	override fun onBindViewHolder(holder: GenreRVHolder, position: Int) {
		holder.bind(genre[position])
	}

	override fun getItemCount(): Int {
		return genre.size
	}

	inner class GenreRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = ItemGenreBinding.bind(itemView)
		fun bind(genre: Genre) {
			binding.tvItemGenre.text = genre.name
		}
	}
}