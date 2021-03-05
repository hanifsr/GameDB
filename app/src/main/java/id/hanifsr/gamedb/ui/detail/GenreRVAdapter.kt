package id.hanifsr.gamedb.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hanifsr.gamedb.databinding.ItemGenreBinding

class GenreRVAdapter : RecyclerView.Adapter<GenreRVAdapter.GenreRVHolder>() {

	var genres: List<String> = emptyList()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreRVHolder {
		return GenreRVHolder.from(parent)
	}

	override fun onBindViewHolder(holder: GenreRVHolder, position: Int) {
		holder.bind(genres[position])
	}

	override fun getItemCount(): Int {
		return genres.size
	}

	class GenreRVHolder private constructor(private val binding: ItemGenreBinding) :
		RecyclerView.ViewHolder(binding.root) {

		companion object {
			fun from(parent: ViewGroup): GenreRVHolder {
				val layoutInflater = LayoutInflater.from(parent.context)
				val binding = ItemGenreBinding.inflate(layoutInflater, parent, false)
				return GenreRVHolder(binding)
			}
		}

		fun bind(genre: String) {
			binding.genre = genre
		}
	}
}