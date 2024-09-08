package com.example.desafiotecnicoflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiotecnicoflow.databinding.ActivityMainBinding
import com.example.desafiotecnicoflow.ui.begin.StartScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,StartScreenFragment())
        fragmentTransaction.commit()
    }
}