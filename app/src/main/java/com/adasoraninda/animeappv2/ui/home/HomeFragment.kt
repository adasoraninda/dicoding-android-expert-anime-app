package com.adasoraninda.animeappv2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.core.ui.ListAnimeAdapter
import com.adasoraninda.animeappv2.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<HomeViewModel>()

    private val listAnimeAdapter by lazy {
        ListAnimeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewList()
        initViewMenu()
        observeViewModel()

        listAnimeAdapter.addLoadStateListener(loadListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.layoutList?.listItem?.adapter = null
        _binding = null
        listAnimeAdapter.removeLoadStateListener(loadListener())
    }

    private fun loadListener(): (CombinedLoadStates) -> Unit = { loadState ->
        val layoutList = binding?.layoutList
        val layoutError = binding?.layoutError

        layoutList?.progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
        layoutList?.listItem?.isVisible = loadState.source.refresh is LoadState.NotLoading
        layoutError?.root?.isVisible = loadState.source.refresh is LoadState.Error

        if (listAnimeAdapter.itemCount <= 0 && loadState.source.refresh is LoadState.NotLoading) {
            layoutError?.root?.isVisible = true
            layoutError?.textError?.text = getString(R.string.error_message)
        } else {
            layoutError?.root?.isVisible = false
        }
    }

    private fun initViewMenu() {
        val toolbar = binding?.toolbar ?: return

        toolbar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_search -> findNavController().navigate(R.id.nav_home_to_search)
                R.id.action_favorite -> findNavController().navigate(R.id.nav_home_to_favorite)
                R.id.action_settings -> findNavController().navigate(R.id.nav_home_to_settings)
            }
            true
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
            val directions = HomeFragmentDirections.navHomeToDetail(id)
            findNavController().navigate(directions)
        }
    }

    private fun observeViewModel() {
        viewModel.anime.observe(viewLifecycleOwner) {
            listAnimeAdapter.submitData(lifecycle, it)
        }
    }


}