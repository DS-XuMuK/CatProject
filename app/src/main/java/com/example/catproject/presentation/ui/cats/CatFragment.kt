package com.example.catproject.presentation.ui.cats

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catproject.appComponent
import com.example.catproject.databinding.FragmentCatsBinding
import com.example.catproject.domain.model.Cat
import com.example.catproject.presentation.base.BaseFragment
import com.example.catproject.presentation.ui.adapter.CatsAdapter
import com.example.catproject.presentation.utils.observeTemplate
import com.example.catproject.presentation.utils.showToast
import kotlinx.coroutines.launch

class CatFragment : BaseFragment<FragmentCatsBinding, CatViewModel>(
    CatViewModel::class.java
), CatsAdapter.CatClickListener {

    private val recyclerView: RecyclerView by lazy {
        binding.recyclerView
    }
    private val gridLayoutManager: GridLayoutManager by lazy {
        GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
    }
    private val catsAdapter: CatsAdapter by lazy {
        CatsAdapter(this, context)
    }

    override fun createBinding() = FragmentCatsBinding.inflate(layoutInflater)

    override fun getDownloadId() = viewModel.downloadId.value

    override fun onCreate(savedInstanceState: Bundle?) {
        context.appComponent.injectCatFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCatsList()
        observeCatsList()
        setupRecyclerView()
    }

    private fun observeCatsList() {
        with(binding) {
            observeTemplate(
                observable = viewModel.catsLiveData,
                onSuccess = {
                    catsAdapter.submitList(it)
                },
                onError = {
                    if (catsAdapter.itemCount < 1) textView.visibility = View.VISIBLE
                    context.showToast(it)
                },
                onLoading = {
                    textView.visibility = View.INVISIBLE
                },
                progressIndicator = progressCircular
            )
        }
    }

    private fun setupRecyclerView() {
        with(recyclerView) {
            layoutManager = gridLayoutManager
            adapter = catsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (gridLayoutManager.findLastVisibleItemPosition() == catsAdapter.itemCount - 1) {
                        viewModel.getCatsList()
                    }
                }
            })
        }
    }

    override fun onFavoriteClick(cat: Cat) {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.clickFavorites(cat)
            }.join()

            val index = catsAdapter.currentList.indexOf(cat)
            catsAdapter.notifyItemChanged(index)
        }
    }

    override fun onDownloadClick(cat: Cat) {
        viewModel.downloadImage(cat)
    }

    companion object {
        fun newInstance() = CatFragment()
    }
}