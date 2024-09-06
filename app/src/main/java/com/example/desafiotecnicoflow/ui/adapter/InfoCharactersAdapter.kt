package com.example.desafiotecnicoflow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.ItemcharacterBinding

class InfoCharactersAdapter (private val characters:List<Character>,
                             private val onClickItem: OnClickListener
): RecyclerView.Adapter<InfoCharactersAdapter.ViewHolder>()  {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemcharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = characters[position]
        with(holder){
            nameCharacter.text = item.name
            desciptionCharacter.text = """${item.status}${item.species}"""
            genderCharacter.text = item.gender
            locationCharacter.text = item.location.name
            Glide.with(context).load(item.image).into(imageCharacter)
            itemView.setOnClickListener {
                onClickItem.onClick(position,item)
            }
        }
    }

    override fun getItemCount(): Int  = characters.size

    class ViewHolder(val binding: ItemcharacterBinding): RecyclerView.ViewHolder(binding.root){

        val imageCharacter =  binding.imgCharacter
        val nameCharacter = binding.nameCharacter
        val desciptionCharacter = binding.descriptionCharacter
        val genderCharacter = binding.genderCharacterDesc
        val locationCharacter = binding.locationCharacter
    }

    interface OnClickListener {
        fun onClick(position: Int, item: Character)
    }
}