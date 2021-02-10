package id.hanifsr.gamedb.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.FragmentSearchBinding
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.viewmodel.ViewModelFactory

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
		/*fragmentSearchBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)*/
		_fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
		return fragmentSearchBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			showMark(true)
			showRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
			searchViewModel.searchGames.observe(viewLifecycleOwner, {
				if (it != null) {
					onSearchGamesFetched(it)
				}
			})

			fragmentSearchBinding.svSearch.setOnQueryTextListener(object :
				SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					if (query != null) {
						searchViewModel.keyword.postValue(query)
					}
					return true
				}

				override fun onQueryTextChange(newText: String?): Boolean {
					if (newText?.isEmpty() == true) {
						searchViewModel.keyword.postValue("")
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
		_fragmentSearchBinding = null
	}

	private fun showRecyclerView() {
		fragmentSearchBinding.rvSearch.setHasFixedSize(true)
		fragmentSearchBinding.rvSearch.layoutManager = LinearLayoutManager(activity)
		searchRVAdapter =
			SearchRVAdapter(emptyList()) { gameEntity -> showSelectedGame(gameEntity) }
		fragmentSearchBinding.rvSearch.adapter = searchRVAdapter
	}

	private fun showSelectedGame(gameEntity: GameEntity) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, gameEntity.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onSearchGamesFetched(gameEntities: List<GameEntity>) {
		searchRVAdapter.updateSearch(gameEntities)
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