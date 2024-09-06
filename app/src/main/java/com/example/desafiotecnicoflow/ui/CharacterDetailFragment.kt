package com.example.desafiotecnicoflow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiotecnicoflow.R
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.FragmentCharacterDetailBinding
import com.example.desafiotecnicoflow.databinding.FragmentCharactersBinding


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
        binding.soloTexto.text = itemCharacter.gender
        return binding.root    }

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