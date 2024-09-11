package com.example.desafiotecnicoflow.ui.home

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersAdapter
import com.example.desafiotecnicoflow.R
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.FragmentCharactersBinding
import com.example.desafiotecnicoflow.ui.adapter.InfoCharacterPagingAdapter
import com.example.desafiotecnicoflow.ui.detail.CharacterDetailFragment
import com.example.desafiotecnicoflow.viewmodel.FlowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharactersFragment : Fragment(), InfoCharactersAdapter.OnClickListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adpterPaging: InfoCharacterPagingAdapter
    private val viewModel: FlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        setUpRv()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingData()
    }

    private fun loadingData() {
        lifecycleScope.launch {
            viewModel.listDate.collect { values ->
                adpterPaging.submitData(values)
            }
        }
    }

    private fun setUpRv() {
        adpterPaging = InfoCharacterPagingAdapter(this)
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(context)
            val orientation = resources.configuration.orientation
            val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
            val layoutGrid = GridLayoutManager(context, spanCount)
            layoutManager = layoutGrid
            binding.rvCharacters.apply {
                val orientation = resources.configuration.orientation
                // Si está en horizontal, configurar 2 columnas, si no, 1 columna
                val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
                val layoutManagerGrid = GridLayoutManager(context, spanCount)
                layoutManager = layoutManagerGrid
                adapter = adpterPaging
                setHasFixedSize(true)
            }

            adpterPaging.addLoadStateListener { loadState ->

                if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                    binding.loader.isVisible = true
                } else {
                    binding.loader.isVisible = false
                    val errorState = when {
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    errorState?.let {
                        binding.errorScreen?.root?.isVisible = true
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
        fun newInstance(param1: String, param2: String) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(position: Int, item: Character?) {
        goToFramnet(item)
    }

    private fun goToFramnet(item: Character?) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CharacterDetailFragment.newInstance(item!!))
            .addToBackStack(null)
            .commit()
    }
}