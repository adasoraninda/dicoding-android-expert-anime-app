package com.adasoraninda.animeappv2.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.ui.FavoriteAnimeAdapter

import com.adasoraninda.animeappv2.di.UseCaseEntryPoint
import com.adasoraninda.animeappv2.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    private val favoriteAnimeAdapter by lazy {
        FavoriteAnimeAdapter()
    }

    private val useCase by lazy {
        EntryPointAccessors.fromApplication(
            requireContext(),
            UseCaseEntryPoint::class.java
        ).animeUseCase()
    }

    private val factory by lazy {
        FavoriteViewModel.factory(useCase)
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        factory
    }

    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewMenu()
        initViewList()
        observeViewModel()

        favoriteAnimeAdapter.addLoadStateListener(loadListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.listItem?.adapter = null
        toast?.cancel()
        _binding = null
        favoriteAnimeAdapter.removeLoadStateListener(loadListener())
    }

    private fun loadListener(): (CombinedLoadStates) -> Unit = { loadState ->
        val listItem = binding?.listItem
        val progressBar = binding?.progressBar
        val groupError = binding?.groupError
        val textError = binding?.textError

        progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
        listItem?.isVisible = loadState.source.refresh is LoadState.NotLoading

        if (loadState.source.refresh is LoadState.NotLoading ||
            loadState.source.refresh is LoadState.Error
        ) {
            val isListNotEmpty = favoriteAnimeAdapter.itemCount > 0
            showMenuDeleteAll(isListNotEmpty)
        }

        if (loadState.source.refresh is LoadState.NotLoading && favoriteAnimeAdapter.itemCount < 1) {
            groupError?.isVisible = true
            textError?.text = getString(R.string.empty_message)
        } else if (loadState.source.refresh is LoadState.Error) {
            groupError?.isVisible = true
            textError?.text = getString(R.string.error_message)
        } else {
            groupError?.isVisible = false
        }
    }

    private fun initViewList() {
        val listItem = binding?.listItem ?: return

        favoriteAnimeAdapter.onItemClick = this::onItemClick
        favoriteAnimeAdapter.onItemRemoveClick = this::onItemRemoveClick

        listItem.layoutManager = LinearLayoutManager(requireContext())
        listItem.adapter = favoriteAnimeAdapter
        listItem.setHasFixedSize(true)
    }

    private fun onItemClick(id: Int?) {
        if (id != null) {
            val directions = FavoriteFragmentDirections.navFavoriteToDetail(id)
            findNavController().navigate(directions)
        }
    }

    private fun onItemRemoveClick(anime: Anime?) {
        if (anime != null) {
            viewModel.removeFavoriteAnime(anime)
            toast = createToast(R.string.un_favorite_an_anime)
            toast?.show()
        }
    }

    private fun initViewMenu() {
        val toolbar = binding?.toolbar ?: return

        toolbar.setOnMenuItemClickListener { menu ->
            if (menu.itemId == R.id.action_delete_all) {
                viewModel.removeAllFavoriteAnime()
                toast = createToast(R.string.un_favorite_all_anime)
                toast?.show()
            }
            true
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showMenuDeleteAll(show: Boolean) {
        val menu = binding?.toolbar?.menu ?: return
        val deleteAllMenu = menu.findItem(R.id.action_delete_all)

        deleteAllMenu.isVisible = show
    }

    private fun observeViewModel() {
        viewModel.favoriteAnime.observe(viewLifecycleOwner) {
            favoriteAnimeAdapter.submitData(lifecycle, it)
        }
    }

    private fun createToast(@StringRes messageRes: Int, length: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(requireContext(), messageRes, length)
    }

}