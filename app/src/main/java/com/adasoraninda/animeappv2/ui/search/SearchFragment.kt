package com.adasoraninda.animeappv2.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.core.ui.ListAnimeAdapter
import com.adasoraninda.animeappv2.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<SearchViewModel>()

    private val listAnimeAdapter by lazy {
        ListAnimeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            initView()
        }

        initViewSearch()
        initViewMenu()
        initViewList()
        observeViewModel()

        listAnimeAdapter.addLoadStateListener(loadListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.layoutList?.listItem?.adapter = null
        _binding = null
        listAnimeAdapter.removeLoadStateListener(loadListener())
    }

    private fun initView() {
        val layoutList = binding?.layoutList ?: return
        val layoutError = binding?.layoutError ?: return

        layoutList.progressBar.isVisible = false
        layoutError.root.isVisible = true
        layoutError.textError.text = getString(R.string.empty_message)
    }

    private fun loadListener(): (CombinedLoadStates) -> Unit = { loadState ->
        val layoutList = binding?.layoutList
        val layoutError = binding?.layoutError

        layoutList?.progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
        layoutList?.listItem?.isVisible = loadState.source.refresh is LoadState.NotLoading

        if (loadState.source.refresh is LoadState.NotLoading && listAnimeAdapter.itemCount < 1) {
            layoutError?.root?.isVisible = true
            layoutError?.textError?.text = getString(R.string.empty_message)
        } else if (loadState.source.refresh is LoadState.Error) {
            layoutError?.root?.isVisible = true
            layoutError?.textError?.text = getString(R.string.error_message)
        } else {
            layoutError?.root?.isVisible = false
        }

    }

    private fun initViewSearch() {
        val searchView = binding?.searchView ?: return

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchAnime(query)
                    binding?.layoutList?.listItem?.scrollToPosition(0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.setSearchQuery(newText)
                }
                return true
            }
        })
    }

    private fun initViewMenu() {
        val toolbar = binding?.toolbar ?: return

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initViewList() {
        val listItem = binding?.layoutList?.listItem ?: return

        listAnimeAdapter.onItemClick = this::onItemClick
        listItem.layoutManager = LinearLayoutManager(requireContext())
        listItem.adapter = listAnimeAdapter
        listItem.setHasFixedSize(true)
    }

    private fun onItemClick(id: Int?) {
        if (id != null) {
            val directions = SearchFragmentDirections.navSearchToDetail(id)
            findNavController().navigate(directions)
        }
    }

    private fun observeViewModel() {
        viewModel.anime.observe(viewLifecycleOwner) {
            listAnimeAdapter.submitData(lifecycle, it)
        }
    }

}