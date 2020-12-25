package id.hanifsr.gamedb.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.databinding.FragmentSearchBinding
import id.hanifsr.gamedb.ui.detail.DetailActivity

class SearchFragment : Fragment() {

	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding!!
	private lateinit var adapter: SearchRVAdapter
	private lateinit var searchViewModel: SearchViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentSearchBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		if (activity != null) {
			showMark(true)
			showRecyclerView()

			searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
			searchViewModel.searchGames().observe(viewLifecycleOwner, {
				if (it != null) {
					onSearchGamesFetched(it)
				}
			})

			binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					if (query != null) {
						searchViewModel.keyword = query
					}
					return true
				}

				override fun onQueryTextChange(newText: String?): Boolean {
					if (newText?.isEmpty() == true) {
						searchViewModel.keyword = ""
					}
					return true
				}

			})
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (data != null) {
			if (requestCode == DetailActivity.REQUEST_DELETE && resultCode == DetailActivity.RESULT_DELETE) {
				val name = data.getStringExtra(DetailActivity.EXTRA_NAME)
				Toast.makeText(activity, "$name deleted from Favourites!", Toast.LENGTH_SHORT)
					.show()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun showRecyclerView() {
		binding.rvSearch.setHasFixedSize(true)
		binding.rvSearch.layoutManager = LinearLayoutManager(activity)
		adapter = SearchRVAdapter(listOf()) { game -> showSelectedGame(game) }
		binding.rvSearch.adapter = adapter
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onSearchGamesFetched(games: List<Game>) {
		adapter.updateSearch(games)
		showMark(false)
	}

	private fun showMark(state: Boolean) {
		if (state) {
			binding.pbSearch.visibility = View.VISIBLE
			binding.svSearch.visibility = View.GONE
		} else {
			binding.pbSearch.visibility = View.GONE
			binding.svSearch.visibility = View.VISIBLE
		}
	}
}