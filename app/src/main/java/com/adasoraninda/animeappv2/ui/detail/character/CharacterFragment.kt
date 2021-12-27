package com.adasoraninda.animeappv2.ui.detail.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.core.ui.CharacterAnimeAdapter
import com.adasoraninda.animeappv2.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<CharacterViewModel>()

    private val characterAnimeAdapter by lazy {
        CharacterAnimeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewList()
        observeViewModel()

        val id = getAnimeId()
        if (id > 0) {
            viewModel.setAnimeId(id)
        }

        characterAnimeAdapter.addLoadStateListener(loadStateListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.layoutList?.listItem?.adapter = null
        _binding = null
        characterAnimeAdapter.removeLoadStateListener(loadStateListener())
    }

    private fun initViewList() {
        val listItem = binding?.layoutList?.listItem ?: return

        listItem.layoutManager = LinearLayoutManager(requireContext())
        listItem.adapter = characterAnimeAdapter
        listItem.setHasFixedSize(true)
    }

    private fun loadStateListener(): (CombinedLoadStates) -> Unit = { loadState ->
        val layoutList = binding?.layoutList
        val layoutError = binding?.layoutError

        layoutList?.progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
        layoutList?.listItem?.isVisible = loadState.source.refresh is LoadState.NotLoading

        if (loadState.source.refresh is LoadState.NotLoading && characterAnimeAdapter.itemCount < 1) {
            layoutError?.root?.isVisible = true
            layoutError?.textError?.text = getString(R.string.empty_message)
        } else if (loadState.source.refresh is LoadState.Error) {
            layoutError?.root?.isVisible = true
            layoutError?.textError?.text = getString(R.string.error_message)
        } else {
            layoutError?.root?.isVisible = false
        }
    }

    private fun getAnimeId(): Int {
        val id = arguments?.getInt(EXTRA_ID, 0)
        return id ?: 0
    }

    private fun observeViewModel() {
        viewModel.animeDetailCharacters.observe(viewLifecycleOwner) {
            characterAnimeAdapter.submitData(lifecycle, it)
        }
    }

    companion object {
        private const val EXTRA_ID = "ID"

        fun getInstance(id: Int?): CharacterFragment =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID, id ?: -1)
                }
            }

    }

}