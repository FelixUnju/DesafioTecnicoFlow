package com.example.desafiotecnicoflow.ui.begin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiotecnicoflow.R
import com.example.desafiotecnicoflow.databinding.FragmentStarScreenBinding
import com.example.desafiotecnicoflow.ui.home.CharactersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartScreenFragment : Fragment() {

    private var _binding: FragmentStarScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStarScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.start.setOnClickListener {
            goToFragment()
        }
    }

    private fun goToFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharactersFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartScreenFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}