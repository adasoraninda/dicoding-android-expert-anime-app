package com.adasoraninda.animeappv2.ui.detail.staff

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
import com.adasoraninda.animeappv2.core.ui.StaffAnimeAdapter
import com.adasoraninda.animeappv2.databinding.FragmentStaffBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffFragment : Fragment() {

    private var _binding: FragmentStaffBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<StaffViewModel>()

    private val staffAnimeAdapter by lazy {
        StaffAnimeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStaffBinding.inflate(inflater, container, false)
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

        staffAnimeAdapter.addLoadStateListener(loadListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.layoutList?.listItem?.adapter = null
        _binding = null
        staffAnimeAdapter.removeLoadStateListener(loadListener())
    }

    private fun initViewList() {
        val listItem = binding?.layoutList?.listItem ?: return

        listItem.layoutManager = LinearLayoutManager(requireContext())
        listItem.adapter = staffAnimeAdapter
        listItem.setHasFixedSize(true)
    }

    private fun loadListener(): (CombinedLoadStates) -> Unit = { loadState ->
        val layoutList = binding?.layoutList
        val layoutError = binding?.layoutError

        layoutList?.progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
        layoutList?.listItem?.isVisible = loadState.source.refresh is LoadState.NotLoading

        if (loadState.source.refresh is LoadState.NotLoading && staffAnimeAdapter.itemCount < 1) {
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
        viewModel.animeDetailStaff.observe(viewLifecycleOwner) {
            staffAnimeAdapter.submitData(lifecycle, it)
        }
    }

    companion object {
        private const val EXTRA_ID = "ID"

        fun getInstance(id: Int?): StaffFragment =
            StaffFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID, id ?: -1)
                }
            }
    }

}