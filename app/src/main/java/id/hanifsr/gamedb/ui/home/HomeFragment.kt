package id.hanifsr.gamedb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.PagerSnapHelper
import id.hanifsr.gamedb.databinding.FragmentHomeBinding
import id.hanifsr.gamedb.domain.Game
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.recyclerview.snap.OnSnapPositionChangeListener
import id.hanifsr.gamedb.util.recyclerview.snap.SnapOnScrollListener
import id.hanifsr.gamedb.util.recyclerview.snap.attachSnapHelperWithListener
import id.hanifsr.gamedb.viewmodel.ViewModelFactory
import id.hanifsr.gamedb.vo.Result

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!
	private lateinit var adapter: HomeRVAdapter
	private lateinit var viewModel: HomeViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			initRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
			viewModel.popularGames.observe(viewLifecycleOwner, {
				when (it) {
					is Result.Loading -> showMark(loading = true, false, null)
					is Result.Success -> onPopularGamesFetched(it.data)
					is Result.Empty -> showMark(loading = false, true, "Games are empty!")
					is Result.Error -> showMark(loading = false, true, it.errorMessage)
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
		binding.rvGames.setHasFixedSize(true)
		adapter = HomeRVAdapter { game -> showSelectedGame(game) }
		binding.rvGames.adapter = adapter
		binding.rvGames.attachSnapHelperWithListener(
			PagerSnapHelper(),
			SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
			object : OnSnapPositionChangeListener {
				override fun onSnapPositionChange(position: Int) {
					binding.game = adapter.games[position]
				}
			})
	}

	private fun showSelectedGame(game: Game) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, game.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onPopularGamesFetched(games: List<Game>) {
		adapter.games = games
		showMark(loading = false, false, null)
	}

	private fun showMark(loading: Boolean, error: Boolean, message: String?) {
		if (loading) {
			binding.pbHome.visibility = View.VISIBLE
			binding.ivStatusHome.visibility = View.GONE
			binding.tvStatusHome.visibility = View.GONE
			binding.tvPopular.visibility = View.GONE
			binding.tvThisMonth.visibility = View.GONE
		} else {
			binding.pbHome.visibility = View.GONE
			if (error) {
				binding.ivStatusHome.visibility = View.VISIBLE
				binding.tvStatusHome.visibility = View.VISIBLE
				binding.tvStatusHome.text = message
				binding.tvPopular.visibility = View.GONE
				binding.tvThisMonth.visibility = View.GONE
			} else {
				binding.tvPopular.visibility = View.VISIBLE
				binding.tvThisMonth.visibility = View.VISIBLE
			}
		}
	}
}