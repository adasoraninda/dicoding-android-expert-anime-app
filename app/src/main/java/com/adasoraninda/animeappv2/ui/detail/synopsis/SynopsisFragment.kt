package com.adasoraninda.animeappv2.ui.detail.synopsis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.databinding.FragmentSynopsisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SynopsisFragment : Fragment() {

    private var _binding: FragmentSynopsisBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<SynopsisViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSynopsisBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setSynopsis(getSynopsis())

        observeViewModel()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getSynopsis(): String {
        val synopsis = arguments?.getString(EXTRA_SYNOPSIS)
        return synopsis ?: getString(R.string.empty_message)
    }

    private fun observeViewModel() {
        viewModel.synopsis.observe(viewLifecycleOwner) { synopsis ->
            binding?.layoutError?.root?.isVisible = synopsis == null
            binding?.textSynopsis?.isVisible = synopsis != null
            binding?.textSynopsis?.text = synopsis
        }
    }

    companion object {
        private const val EXTRA_SYNOPSIS = "SYNOPSIS"

        fun getInstance(synopsis: String?): SynopsisFragment =
            SynopsisFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SYNOPSIS, synopsis)
                }
            }
    }

}