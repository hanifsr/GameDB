package id.hanifsr.gamedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.hanifsr.gamedb.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

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
		searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
		searchViewModel.text.observe(viewLifecycleOwner, {
			tv_search.text = it
		})

		sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				Toast.makeText(activity, query, Toast.LENGTH_SHORT).show()
				return true
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				return false
			}

		})
	}
}