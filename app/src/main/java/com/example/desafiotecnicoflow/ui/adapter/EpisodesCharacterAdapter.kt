package com.example.desafiotecnicoflow.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotecnicoflow.data.EpisodeModel
import com.example.desafiotecnicoflow.databinding.ItemEpisodeBinding
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersAdapter.OnClickListener

class EpisodesCharacterAdapter(private val episodes: List<EpisodeModel>, private val onClickItem: OnClickListener): RecyclerView.Adapter<EpisodesCharacterAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodesCharacterAdapter.ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesCharacterAdapter.ViewHolder, position: Int) {
        val item = episodes[position]
        holder.nameEpisode.text = item.name
        holder.airDateEpisode.text = item.airDate
        holder.episodeSE.text = item.episode
        holder.itemView.setOnClickListener {
            onClickItem.onClick(position,null)
        }
    }

    override fun getItemCount(): Int = episodes.size

    class ViewHolder(val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root){
        val imageEpisode = binding.imgEpisode
        val nameEpisode = binding.nameEpisodeDetail
        val airDateEpisode = binding.airDateDetail
        val episodeSE = binding.episodeSEDetail
    }
}