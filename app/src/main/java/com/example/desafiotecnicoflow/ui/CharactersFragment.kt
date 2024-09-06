package com.example.desafiotecnicoflow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotecnicoflow.InfoCharactersAdapter
import com.example.desafiotecnicoflow.R
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.FragmentCharactersBinding
import com.example.desafiotecnicoflow.repository.FlowRepository
import com.example.desafiotecnicoflow.service.FlowService
import com.example.desafiotecnicoflow.viewmodel.FlowViewModel
import com.example.desafiotecnicoflow.viewmodel.ViewModelFactory


class CharactersFragment : Fragment(), InfoCharactersAdapter.OnClickListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterCharacter: InfoCharactersAdapter
    lateinit var viewModel: FlowViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flowRepository = FlowRepository(FlowService.getInstance())
        viewModel = ViewModelProvider(this, ViewModelFactory(flowRepository)).get(FlowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.infoCharacters.observe(viewLifecycleOwner){
            adapterCharacter = InfoCharactersAdapter(it.results,this)
            binding.rvCharacters.apply {
                adapter = adapterCharacter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner){
            if (it){
                Log.i("TONIO",it.toString())
            }else{
                Log.i("TONIO",it.toString())
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(position: Int, item: Character) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharacterDetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }
}