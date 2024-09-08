package com.example.desafiotecnicoflow.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.FragmentCharacterDetailBinding
import com.example.desafiotecnicoflow.repository.FlowRepository
import com.example.desafiotecnicoflow.service.FlowService
import com.example.desafiotecnicoflow.ui.adapter.EpisodesCharacterAdapter
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersAdapter
import com.example.desafiotecnicoflow.utils.NetworkResult
import com.example.desafiotecnicoflow.viewmodel.FlowViewModel
import com.example.desafiotecnicoflow.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch


class CharacterDetailFragment : Fragment(),InfoCharactersAdapter.OnClickListener {

    private lateinit var itemCharacter: Character
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FlowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flowRepository = FlowRepository(FlowService.getInstance())
        viewModel = ViewModelProvider(this, ViewModelFactory(flowRepository)).get(FlowViewModel::class.java)

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

        with(binding){
            Glide.with(requireContext()).load(itemCharacter.image).into(imgCharacter)
            descriptionCharacter.text = """${itemCharacter.status} - ${itemCharacter.species}"""
            genderCharacterDesc.text = itemCharacter.gender
            locationCharacterDesc.text = itemCharacter.location.name
            nameCharacter.text = itemCharacter.name
            progressEpisodes.visibility = View.VISIBLE
            lifecycleScope.launch {
                //viewModel.getEpisodesCharacter(itemCharacter.episode)
                viewModel.getEpisodesResult(itemCharacter.episode)
            }
            /*
            viewModel.episodeCharacters.observe(viewLifecycleOwner){res ->
               rvEpisodes.apply {
                   adapter =  EpisodesCharacterAdapter(res,this@CharacterDetailFragment)
                   layoutManager = LinearLayoutManager(context)
                   setHasFixedSize(true)
                   progressEpisodes.visibility = View.GONE
               }
            }

             */

            viewModel.episodeResult.observe(viewLifecycleOwner){res ->
                progressEpisodes.isVisible = false
                when(res){
                    is NetworkResult.Success ->{
                        rvEpisodes.apply {
                            adapter =  EpisodesCharacterAdapter(res.data!!,this@CharacterDetailFragment)
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                        }
                    }
                    is NetworkResult.Error ->{
                        Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResult.Loading ->{
                        progressEpisodes.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(item: Character) =
            CharacterDetailFragment().apply {
                itemCharacter = item
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