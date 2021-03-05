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
import id.hanifsr.gamedb.databinding.FragmentSearchBinding
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.viewmodel.ViewModelFactory
import id.hanifsr.gamedb.vo.Result

class SearchFragment : Fragment() {

	private var _fragmentSearchBinding: FragmentSearchBinding? = null
	private val fragmentSearchBinding get() = _fragmentSearchBinding!!
	private lateinit var searchRVAdapter: SearchRVAdapter
	private lateinit var searchViewModel: SearchViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
		return fragmentSearchBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			initRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
			searchViewModel.searchGames.observe(viewLifecycleOwner, {
				when (it) {
					is Result.Loading -> showMark(true)
					is Result.Success -> onSearchGamesFetched(it.data)
					is Result.Empty -> Toast.makeText(
						activity,
						"Search result is 0!",
						Toast.LENGTH_LONG
					).show()
					is Result.Error -> Toast.makeText(activity, it.errorMessage, Toast.LENGTH_LONG)
						.show()
				}
			})

			fragmentSearchBinding.svSearch.setOnQueryTextListener(object :
				SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					if (query != null) {
						searchViewModel.keyword.postValue(query)
					}
					return false
				}

				override fun onQueryTextChange(newText: String?): Boolean {
					if (newText?.isEmpty() == true) {
						searchViewModel.keyword.postValue("")
					}
					return false
				}

			})
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (data != null) {
			if (requestCode == DetailActivity.REQUEST_DELETE && resultCode == DetailActivity.RESULT_DELETE) {
				val name = data.getStringExtra(DetailActivity.EXTRA_NAME)
				Toast.makeText(activity, "$name is removed from Favourites!", Toast.LENGTH_SHORT)
					.show()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_fragmentSearchBinding = null
	}

	private fun initRecyclerView() {
		fragmentSearchBinding.rvSearch.setHasFixedSize(true)
		searchRVAdapter =
			SearchRVAdapter { game -> showSelectedGame(game) }
		fragmentSearchBinding.rvSearch.adapter = searchRVAdapter
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onSearchGamesFetched(games: List<Game>) {
		searchRVAdapter.games = games
		showMark(false)
	}

	private fun showMark(state: Boolean) {
		if (state) {
			fragmentSearchBinding.pbSearch.visibility = View.VISIBLE
			fragmentSearchBinding.svSearch.visibility = View.GONE
		} else {
			fragmentSearchBinding.pbSearch.visibility = View.GONE
			fragmentSearchBinding.svSearch.visibility = View.VISIBLE
		}
	}
}