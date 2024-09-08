package com.example.desafiotecnicoflow.ui.detail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.FragmentCharacterDetailBinding
import com.example.desafiotecnicoflow.ui.adapter.EpisodesCharacterAdapter
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersAdapter
import com.example.desafiotecnicoflow.utils.Constants
import com.example.desafiotecnicoflow.utils.NetworkResult
import com.example.desafiotecnicoflow.viewmodel.FlowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(), InfoCharactersAdapter.OnClickListener {

    private lateinit var itemCharacter: Character
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemCharacter = it.getSerializable("item") as Character
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        initViewModel()
    }
    private fun initViewModel() {

        with(binding) {
            lifecycleScope.launch {
                viewModel.getEpisodesResult(itemCharacter.episode)
            }

            viewModel.episodeResult.observe(viewLifecycleOwner) { res ->
                progressEpisodes.isVisible = false
                when (res) {
                    is NetworkResult.Success -> {
                        rvEpisodes.apply {
                            adapter = EpisodesCharacterAdapter(res.data!!, this@CharacterDetailFragment)
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                        }
                    }
                    is NetworkResult.Error -> {
                        binding.errorScreen?.root?.isVisible = true
                    }
                    is NetworkResult.Loading -> {
                        progressEpisodes.isVisible = true
                    }
                }
            }
        }
    }

    private fun setViews() {
        with(binding) {
            Glide.with(requireContext()).load(itemCharacter.image).into(imgCharacter)
            descriptionCharacter.text = """${itemCharacter.status} - ${itemCharacter.species}"""
            genderCharacterDesc.text = itemCharacter.gender
            locationCharacterDesc.text = itemCharacter.location.name
            nameCharacter.text = itemCharacter.name
            progressEpisodes.visibility = View.VISIBLE
            when{
                itemCharacter.status.equals(Constants.ALIVE) -> setColor(circleStatus, Color.GREEN)
                itemCharacter.status.equals(Constants.DEAD) -> setColor(circleStatus, Color.RED)
                itemCharacter.status.equals(Constants.UNKNOWN) -> setColor(circleStatus, Color.GRAY)
            }
        }
    }

    private fun setColor(circleStatus: View, color: Int) {
        val background = circleStatus.background as GradientDrawable
        background.setColor(color)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(item: Character) =
            CharacterDetailFragment().apply {
                arguments= Bundle().apply {
                    putSerializable("item",item)
                }
            }
    }

    override fun onClick(position: Int, item: Character?) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://web.app.flow.com.ar/")
        )
        startActivity(urlIntent)
    }
}