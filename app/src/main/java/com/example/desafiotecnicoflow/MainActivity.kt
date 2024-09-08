package com.example.desafiotecnicoflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiotecnicoflow.databinding.ActivityMainBinding
import com.example.desafiotecnicoflow.ui.begin.StartScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val currentFragmentKey = "currentFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val fragmentTransaction =supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container,StartScreenFragment())
            fragmentTransaction.commit()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        currentFragment?.let {
            supportFragmentManager.putFragment(outState, currentFragmentKey, it)
        }
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currentFragment = supportFragmentManager.getFragment(savedInstanceState, currentFragmentKey)
        currentFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, it)
                .commit()
        }
    }
}