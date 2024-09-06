package com.example.desafiotecnicoflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.databinding.ActivityMainBinding
import com.example.desafiotecnicoflow.repository.FlowRepository
import com.example.desafiotecnicoflow.service.FlowService
import com.example.desafiotecnicoflow.ui.CharactersFragment
import com.example.desafiotecnicoflow.viewmodel.FlowViewModel
import com.example.desafiotecnicoflow.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,CharactersFragment())
        fragmentTransaction.commit()
    }
}