package com.adasoraninda.animeappv2.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adasoraninda.animeappv2.core.utils.changeTheme
import com.adasoraninda.animeappv2.di.UseCaseEntryPoint
import com.adasoraninda.animeappv2.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.EntryPointAccessors

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding

    private val useCase by lazy {
        EntryPointAccessors.fromApplication(
            requireContext(),
            UseCaseEntryPoint::class.java
        ).changeThemeUseCase()
    }

    private val factory by lazy {
        SettingsViewModel.factory(useCase)
    }

    private val viewModel by viewModels<SettingsViewModel> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewMenu()
        switchListener()
        observeViewModel()

        if (savedInstanceState == null) {
            binding?.switchTheme?.isChecked = viewModel.getTheme()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun switchListener() {
        val switch = binding?.switchTheme ?: return

        switch.setOnCheckedChangeListener { _, b ->
            viewModel.saveTheme(b)
        }
    }

    private fun observeViewModel() {
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDarkTheme ->
            changeTheme(isDarkTheme)
        }
    }

    private fun initViewMenu() {
        val toolbar = binding?.toolbar ?: return

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}