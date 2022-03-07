package py.com.alemiranda95.zemoga.ui.presentation.main.postinfo

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.databinding.FragmentPostInfoBinding
import py.com.alemiranda95.zemoga.di.factory.PostInfoViewModelAssistedFactory
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.ui.adapter.CommentAdapter
import py.com.alemiranda95.zemoga.ui.presentation.BaseBottomSheetDialogFragment
import py.com.alemiranda95.zemoga.utils.RxBus
import py.com.alemiranda95.zemoga.utils.extension.clear
import py.com.alemiranda95.zemoga.utils.extension.showToast
import javax.inject.Inject

@AndroidEntryPoint
class PostInfoFragment : BaseBottomSheetDialogFragment<FragmentPostInfoBinding>(FragmentPostInfoBinding::inflate) {

    companion object {
        const val POST_ARG = "POST_ARG"
        fun newInstance(postInfo: PostInfo): PostInfoFragment {
            val fragment = PostInfoFragment()

            val args = Bundle()
            args.putParcelable(POST_ARG, postInfo)
            fragment.arguments = args

            return fragment
        }
    }

    @Inject
    lateinit var assistedFactory: PostInfoViewModelAssistedFactory

    private val viewModel: PostInfoViewModel by viewModels {
        PostInfoViewModel.Factory(assistedFactory, arguments?.getParcelable(POST_ARG)!!)
    }

    private lateinit var commentAdapter: CommentAdapter

    override fun setViews() {
        binding.postInfoVM = viewModel
        binding.lifecycleOwner = this

        commentAdapter = CommentAdapter(requireContext())
        binding.recyclerviewPostInfoComments.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = commentAdapter
        }
        binding.recyclerviewPostInfoComments.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun setObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            binding.toolbar.title = viewState.post?.title

            if (viewState.postUpdated) {
                RxBus.postActionSubject.onNext(viewState.postAction!!)
            }

            if (viewState.commentListLoaded) {
                with (viewState.post!!.commentList) {
                    commentAdapter.dataList = this
                }
            }

            viewState.errorMessage?.also {
                showToast(it)
            }

            viewState.infoMessage?.also {
                showToast(it)
            }

            binding.fabPostFavorite.setImageDrawable(
                ContextCompat.getDrawable(requireContext(),
                    if (viewState.post!!.favorite)
                        R.drawable.ic_baseline_star_24
                    else
                        R.drawable.ic_baseline_star_border_24
                )
            )

            binding.collapsingToolbarPostInfo.setBackgroundColor(
                ContextCompat.getColor(requireContext(),
                    if (viewState.post.favorite)
                        R.color.favorite_cardview_color
                    else
                        R.color.colorPrimary
                )
            )

            binding.progressLoading.visibility =
                if (viewState.showLoading) View.VISIBLE else View.GONE
        })
    }

    override fun setListeners() {
        binding.fabPostFavorite.setOnClickListener {
            viewModel.setFavorite()
        }

        binding.fabPostDelete.setOnClickListener {
            viewModel.deletePost()
            dismiss()
        }
    }

    override fun onDestroyView() {
        binding.recyclerviewPostInfoComments.clear()
        viewModel.onDestroy()
        super.onDestroyView()
    }
}