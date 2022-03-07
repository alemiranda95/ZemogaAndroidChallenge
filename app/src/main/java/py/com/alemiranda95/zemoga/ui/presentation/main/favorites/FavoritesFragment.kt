package py.com.alemiranda95.zemoga.ui.presentation.main.favorites

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable
import py.com.alemiranda95.zemoga.databinding.FragmentFavoritesBinding
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.ui.adapter.PostAdapter
import py.com.alemiranda95.zemoga.ui.interfaces.IBasePostsFragment
import py.com.alemiranda95.zemoga.ui.presentation.BaseFragment
import py.com.alemiranda95.zemoga.ui.presentation.main.MainActivity
import py.com.alemiranda95.zemoga.ui.presentation.main.postinfo.PostInfoFragment
import py.com.alemiranda95.zemoga.ui.view.ExtendedFabScrollListener
import py.com.alemiranda95.zemoga.utils.PostAction
import py.com.alemiranda95.zemoga.utils.extension.clear
import py.com.alemiranda95.zemoga.utils.extension.showErrorSnackbar
import py.com.alemiranda95.zemoga.utils.extension.showToast

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate),
    PostAdapter.OnClickListener, IBasePostsFragment {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter
    private var postInfoDisposable: Disposable? = null

    override fun setViews() {
        binding.favoritesVM = viewModel
        binding.lifecycleOwner = this

        postAdapter = PostAdapter(requireContext(), this)
        binding.recyclerviewFavorites.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postAdapter
        }
    }

    override fun setObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState.favoritesListLoaded) {
                with (viewState.favoritesList) {
                    postAdapter.dataList = this
                }
            }

            viewState.errorMessage?.also {
                showErrorSnackbar(it, false) {}
            }

            with ((activity as MainActivity).binding.fabDeleteAll) {
                if (viewState.favoritesList.isEmpty()) this.hide() else this.show()
            }

            viewState.infoMessage?.also {
                showToast(it)
            }
        })
    }

    override fun setListeners() {
        binding.recyclerviewFavorites.addOnScrollListener(
            ExtendedFabScrollListener(fab = lazy {
                (activity as MainActivity).binding.fabDeleteAll
            })
        )
    }

    override fun onClick(postInfo: PostInfo) {
        postInfoDisposable?.dispose()
        postInfoDisposable = postActionSubject.subscribe {
            when(it) {
                is PostAction.Update -> viewModel.updatePost(postInfo)
                is PostAction.Delete -> viewModel.deletePost(postInfo)
            }
        }
        PostInfoFragment.newInstance(postInfo).show(parentFragmentManager, null)
    }

    override fun deleteAll() {
        viewModel.deleteAll()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPostsFromDB()
    }

    override fun onDestroyView() {
        binding.recyclerviewFavorites.clear()
        postInfoDisposable?.dispose()
        viewModel.onDestroy()
        super.onDestroyView()
    }

}