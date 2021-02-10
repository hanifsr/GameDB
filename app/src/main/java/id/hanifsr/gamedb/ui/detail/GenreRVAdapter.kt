package id.hanifsr.gamedb.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.databinding.ItemGenreBinding

class GenreRVAdapter(
	private var genres: List<String>
) : RecyclerView.Adapter<GenreRVAdapter.GenreRVHolder>() {

	fun updateGenre(genres: List<String>) {
		this.genres = genres
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreRVHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_genre, parent, false)
		return GenreRVHolder(view)
	}

	override fun onBindViewHolder(holder: GenreRVHolder, position: Int) {
		holder.bind(genres[position])
	}

	override fun getItemCount(): Int {
		return genres.size
	}

	inner class GenreRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val binding = ItemGenreBinding.bind(itemView)
		fun bind(genre: String) {
			binding.tvItemGenre.text = genre
		}
	}
}