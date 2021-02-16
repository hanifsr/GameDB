package id.hanifsr.gamedb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import id.hanifsr.gamedb.data.source.local.entity.GameEntity
import id.hanifsr.gamedb.databinding.FragmentHomeBinding
import id.hanifsr.gamedb.ui.detail.DetailActivity
import id.hanifsr.gamedb.util.recyclerview.OnSnapPositionChangeListener
import id.hanifsr.gamedb.util.recyclerview.SnapOnScrollListener
import id.hanifsr.gamedb.util.recyclerview.attachSnapHelperWithListener
import id.hanifsr.gamedb.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

	private var _fragmentHomeBinding: FragmentHomeBinding? = null
	private val fragmentHomeBinding get() = _fragmentHomeBinding!!
	private lateinit var homeRVAdapter: HomeRVAdapter
	private lateinit var homeViewModel: HomeViewModel

	private var gameEntities: List<GameEntity> = emptyList()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		/*fragmentHomeBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)*/
		_fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
		return fragmentHomeBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			showMark(true)
			showRecyclerView()

			val factory = ViewModelFactory.getInstance(requireActivity())
			homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
			homeViewModel.popularGames.observe(viewLifecycleOwner, {
				if (it != null) {
					onPopularGamesFetched(it)
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
		_fragmentHomeBinding = null
	}

	private fun showRecyclerView() {
		fragmentHomeBinding.rvGames.setHasFixedSize(true)
		fragmentHomeBinding.rvGames.layoutManager =
			LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
		homeRVAdapter = HomeRVAdapter(emptyList()) { gameEntity -> showSelectedGame(gameEntity) }
		fragmentHomeBinding.rvGames.adapter = homeRVAdapter

		val snapHelper = PagerSnapHelper()
		fragmentHomeBinding.rvGames.attachSnapHelperWithListener(snapHelper,
			SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
			object : OnSnapPositionChangeListener {
				override fun onSnapPositionChange(position: Int) {
					fragmentHomeBinding.tvTitle.text = gameEntities[position].name
					fragmentHomeBinding.tvGenre.text = gameEntities[position].genres
				}
			})
	}

	private fun showSelectedGame(gameEntity: GameEntity) {
		val intent = Intent(activity, DetailActivity::class.java)
		intent.putExtra(DetailActivity.EXTRA_ID, gameEntity.id)
		startActivityForResult(intent, DetailActivity.REQUEST_DELETE)
	}

	private fun onPopularGamesFetched(gameEntities: List<GameEntity>) {
		homeRVAdapter.updateGames(gameEntities)
		this.gameEntities = gameEntities
		showMark(false)
	}

	private fun showMark(state: Boolean) {
		if (state) {
			fragmentHomeBinding.pbHome.visibility = View.VISIBLE
			fragmentHomeBinding.tvPopular.visibility = View.GONE
			fragmentHomeBinding.tvThisMonth.visibility = View.GONE
		} else {
			fragmentHomeBinding.pbHome.visibility = View.GONE
			fragmentHomeBinding.tvPopular.visibility = View.VISIBLE
			fragmentHomeBinding.tvThisMonth.visibility = View.VISIBLE
		}
	}
}