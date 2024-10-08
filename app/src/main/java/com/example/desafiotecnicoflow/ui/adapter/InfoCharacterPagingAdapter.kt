package com.example.desafiotecnicoflow.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.ItemcharacterBinding
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersAdapter.OnClickListener
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersAdapter.ViewHolder
import com.example.desafiotecnicoflow.utils.Constants.ALIVE
import com.example.desafiotecnicoflow.utils.Constants.DEAD
import com.example.desafiotecnicoflow.utils.Constants.UNKNOWN

class InfoCharacterPagingAdapter(private val onClickItem: OnClickListener): PagingDataAdapter<Character, InfoCharacterPagingAdapter.MyViewHolder>(diffCallback) {

    private lateinit var context: Context

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            nameCharacter.text = item?.name
            descriptionCharacter.text = """${item?.status} - ${item?.species}"""
            genderCharacterDesc.text = item?.gender
            locationCharacterDesc.text = item?.location?.name
            Glide.with(context).load(item?.image).into(imgCharacter)
            when{
                item?.status.equals(ALIVE) -> setColor(circleStatus, Color.GREEN)
                item?.status.equals(DEAD) -> setColor(circleStatus,Color.RED)
                item?.status.equals(UNKNOWN) -> setColor(circleStatus,Color.GRAY)
            }
        }
        holder.itemView.setOnClickListener {
            onClickItem.onClick(position,item!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemcharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(val binding: ItemcharacterBinding) : RecyclerView.ViewHolder(binding.root){
    }

    private fun setColor(circleStatus: View, color: Int) {
        val background = circleStatus.background as GradientDrawable
        background.setColor(color)
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Character>(){
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return  oldItem.id == newItem.id
            }
        }
    }
}