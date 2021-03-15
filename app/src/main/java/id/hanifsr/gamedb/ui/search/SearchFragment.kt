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

	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding!!
	private lateinit var adapter: SearchRVAdapter
	private lateinit var viewModel: SearchViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentSearchBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			initRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
			viewModel.searchGames.observe(viewLifecycleOwner, {
				when (it) {
					is Result.Loading -> showMark(loading = true, false, null)
					is Result.Success -> onSearchGamesFetched(it.data)
					is Result.Empty -> showMark(loading = false, true, "Search result is 0!")
					is Result.Error -> showMark(loading = false, true, it.errorMessage)
				}
			})

			binding.svSearch.setOnQueryTextListener(object :
				SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					if (query != null) {
						viewModel.keyword.postValue(query)
					}
					return false
				}

				override fun onQueryTextChange(newText: String?): Boolean {
					if (newText?.isEmpty() == true) {
						viewModel.keyword.postValue("")
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
		_binding = null
	}

	private fun initRecyclerView() {
		binding.rvSearch.setHasFixedSize(true)
		adapter =
			SearchRVAdapter { game -> showSelectedGame(game) }
		binding.rvSearch.adapter = adapter
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onSearchGamesFetched(games: List<Game>) {
		adapter.games = games
		showMark(loading = false, false, null)
	}

	private fun showMark(loading: Boolean, error: Boolean, message: String?) {
		if (loading) {
			binding.pbSearch.visibility = View.VISIBLE
			binding.ivStatusSearch.visibility = View.GONE
			binding.tvStatusSearch.visibility = View.GONE
			binding.svSearch.visibility = View.GONE
		} else {
			binding.pbSearch.visibility = View.GONE
			if (error) {
				binding.ivStatusSearch.visibility = View.VISIBLE
				binding.tvStatusSearch.visibility = View.VISIBLE
				binding.tvStatusSearch.text = message
				binding.svSearch.visibility = View.GONE
			} else {
				binding.svSearch.visibility = View.VISIBLE
			}
		}
	}
}