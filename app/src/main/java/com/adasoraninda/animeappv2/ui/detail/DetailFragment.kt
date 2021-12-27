package com.adasoraninda.animeappv2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.BlurTransformation
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.core.data.source.Resource
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.core.ui.ViewPagerAdapter
import com.adasoraninda.animeappv2.databinding.FragmentDetailBinding
import com.adasoraninda.animeappv2.ui.detail.character.CharacterFragment
import com.adasoraninda.animeappv2.ui.detail.information.InformationFragment
import com.adasoraninda.animeappv2.ui.detail.staff.StaffFragment
import com.adasoraninda.animeappv2.ui.detail.synopsis.SynopsisFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewMenu()
        initViewToolbar()
        observeViewModel()

        if (savedInstanceState == null) {
            viewModel.setAnimeId(args.animeId)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.viewPager?.adapter = null
        _binding = null
        toast?.cancel()
    }

    private fun initTabLayout(detailAnime: AnimeDetail?) {
        val tabLayout = binding?.tabLayout ?: return
        val viewPager = binding?.viewPager ?: return

        val listFragment = DetailTab.getListFragment(detailAnime)
        viewPager.adapter = ViewPagerAdapter(listFragment, this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(DetailTab.getTitles()[position])
        }.attach()
    }

    private fun initViewMenu() {
        binding?.toolbar?.setOnMenuItemClickListener { menu ->
            if (menu.itemId == R.id.action_favorite) {
                viewModel.toggleFavorite()

            }
            true
        }
    }

    private fun initViewToolbar() {
        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun changeMenu(isFavorite: Boolean) {
        val menu = binding?.toolbar?.menu ?: return
        val menuFavorite = menu.findItem(R.id.action_favorite)

        val drawable = if (isFavorite) {
            R.drawable.ic_filled_favorite
        } else {
            R.drawable.ic_twotone_favorite
        }

        menuFavorite.setIcon(drawable)
    }

    private fun initData(data: AnimeDetail?) {
        val anime = data ?: return
        val header = binding?.layoutHeaderDetail ?: return
        val info = header.layoutHeaderInfoDetail

        binding?.toolbar?.title = anime.title

        header.textTitle.text = anime.title
        info.textScore.text = anime.score.toString()
        info.textUsers.text = getString(R.string.user_format, anime.scoredBy)
        info.textRanked.text = getString(R.string.tag_format, anime.rank)
        info.textPopularity.text = getString(R.string.tag_format, anime.popularity)
        info.textMembers.text = getString(R.string.tag_format, anime.members)
        info.textSeason.text = anime.premiered
        info.textType.text = anime.type
        info.textStudio.text = anime.studios

        header.imageBanner.load(anime.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_image_load)
            error(R.drawable.ic_image_error)
            transformations(BlurTransformation(requireContext(), 5f))
        }

        header.imagePoster.load(anime.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_image_load)
            error(R.drawable.ic_image_error)
        }

    }

    private fun showFavoriteMessage(isFavorite: Boolean) {
        toast = if (isFavorite) {
            createToast(R.string.favored_an_anime)
        } else {
            createToast(R.string.un_favorite_an_anime)
        }

        toast?.show()
    }

    private fun createToast(@StringRes messageRes: Int, length: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(requireContext(), messageRes, length)
    }

    private fun observeViewModel() {
        viewModel.favoriteMessage.observe(viewLifecycleOwner) { favorite ->
            if (favorite != null) {
                showFavoriteMessage(favorite)
            }
        }

        viewModel.isAnimeFavored.observe(viewLifecycleOwner) { favorite ->
            changeMenu(favorite)
        }

        viewModel.disableButton.observe(viewLifecycleOwner) { disabled ->
            val menu = binding?.toolbar?.menu
            val favorite = menu?.findItem(R.id.action_favorite)

            favorite?.isEnabled = !disabled
        }

        viewModel.animeDetail.observe(viewLifecycleOwner) { resources ->
            binding?.progressBar?.isVisible = resources is Resource.Loading
            binding?.layoutError?.root?.isVisible = resources is Resource.Error

            if (resources is Resource.Success) {
                initData(resources.data)
                initTabLayout(resources.data)
            }

            if (resources is Resource.Error) {
                viewModel.disableButton()

                binding?.toolbar?.menu?.children?.forEach { it.isVisible = false }
                binding?.layoutHeaderDetail?.root?.isVisible = false
                binding?.tabLayout?.isVisible = false
                binding?.viewPager?.isVisible = false

                binding?.layoutError?.textError?.text =
                    resources.message ?: getString(R.string.error_message)
            }
        }

    }

    private object DetailTab {
        fun getTitles() = listOf(
            R.string.synopsis,
            R.string.information,
            R.string.character,
            R.string.staff,
        )

        fun getListFragment(detailAnime: AnimeDetail?) = listOf(
            SynopsisFragment.getInstance(detailAnime?.synopsis),
            InformationFragment.getInstance(detailAnime),
            CharacterFragment.getInstance(detailAnime?.malId),
            StaffFragment.getInstance(detailAnime?.malId),
        )
    }

}