package com.example.desafiotecnicoflow.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.FragmentCharacterDetailBinding


class CharacterDetailFragment : Fragment() {

    private lateinit var itemCharacter: Character
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}