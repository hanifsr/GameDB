package id.hanifsr.gamedb.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.hanifsr.gamedb.R
import id.hanifsr.gamedb.data.model.Game
import id.hanifsr.gamedb.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

	private lateinit var adapter: SearchRVAdapter
	private lateinit var searchViewModel: SearchViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_search, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		if (activity != null) {
			showRecyclerView()

			searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
			searchViewModel.searchGames().observe(viewLifecycleOwner, {
				if (it != null) {
					onSearchGamesFetched(it)
				}
			})

			sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

	private fun showRecyclerView() {
		rv_search.setHasFixedSize(true)
		rv_search.layoutManager = LinearLayoutManager(activity)
		adapter = SearchRVAdapter(listOf()) { game -> showSelectedGame(game) }
		rv_search.adapter = adapter
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivity(intent)
	}

	private fun onSearchGamesFetched(games: List<Game>) {
		adapter.updateSearch(games)
	}
}