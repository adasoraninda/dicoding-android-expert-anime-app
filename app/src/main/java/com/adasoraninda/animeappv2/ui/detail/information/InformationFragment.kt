package com.adasoraninda.animeappv2.ui.detail.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adasoraninda.animeappv2.R
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.databinding.FragmentInformationBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<InformationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setDetailAnime(getAnimeData())
        observeViewModel()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAnimeData(): AnimeDetail? {
        return arguments?.getParcelable(EXTRA_INFO)
    }

    private fun setAnimeData(animeDetail: AnimeDetail?) {

        if (animeDetail == null) {
            val layoutInfo = binding?.containerInfo
            val layoutError = binding?.layoutError

            layoutInfo?.isVisible = false
            layoutError?.root?.isVisible = true
            layoutError?.textError?.text = getString(R.string.empty_message)
            return
        }

        val layoutTitle = binding?.layoutTitle ?: return
        layoutTitle.textEnglish.text = animeDetail.titleEnglish
        layoutTitle.textSynonyms.text = animeDetail.titleSynonyms
        layoutTitle.textJapanese.text = animeDetail.titleJapanese

        val layoutInfo = binding?.layoutInfo ?: return
        layoutInfo.textType.text = animeDetail.type
        layoutInfo.textEpisodes.text = animeDetail.episodes.toString()
        layoutInfo.textStatus.text = animeDetail.status
        layoutInfo.textAired.text = animeDetail.aired
        layoutInfo.textPremiered.text = animeDetail.premiered
        layoutInfo.textBroadcast.text = animeDetail.broadcast
        layoutInfo.textProducers.text = animeDetail.producers
        layoutInfo.textLicensors.text = animeDetail.licensors
        layoutInfo.textStudios.text = animeDetail.studios
        layoutInfo.textSource.text = animeDetail.source
        layoutInfo.textGenres.text = animeDetail.genres
        layoutInfo.textDuration.text = animeDetail.duration
        layoutInfo.textRating.text = animeDetail.rating

        val layoutStats = binding?.layoutStats ?: return
        layoutStats.textScore.text = animeDetail.score.toString()
        layoutStats.textRanked.text = getString(R.string.tag_format, animeDetail.rank)
        layoutStats.textPopularity.text = getString(R.string.tag_format, animeDetail.popularity)
        layoutStats.textFavorites.text = animeDetail.favorites.toString()
        layoutStats.textMembers.text = animeDetail.members.toString()
    }

    private fun observeViewModel() {
        viewModel.detailAnime.observe(viewLifecycleOwner) { animeDetail ->
            setAnimeData(animeDetail)
        }
    }

    companion object {
        private const val EXTRA_INFO = "INFO"

        fun getInstance(detailAnime: AnimeDetail?): InformationFragment =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_INFO, detailAnime)
                }
            }
    }

}