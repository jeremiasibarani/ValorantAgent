package com.example.weapons.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.source.remote.NetworkResult
import com.example.weapons.databinding.ActivityWeaponsBinding
import com.example.weapons.di.viewModelModule
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class WeaponsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWeaponsBinding
    private val loadFeatures by lazy { loadKoinModules(viewModelModule) }
    private val viewModel : WeaponsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        loadFeatures
        super.onCreate(savedInstanceState)
        binding = ActivityWeaponsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.weapons.collect{ result ->
                when(result){
                    is NetworkResult.Loading -> {
                        displayViewsMatchingState(isLoading = true, isError = false)
                    }
                    is NetworkResult.Error -> {
                        displayViewsMatchingState(isLoading = false, isError = true)
                        binding.rvWeapons.visibility = View.VISIBLE
                    }
                    is NetworkResult.Success -> {
                        displayViewsMatchingState(isLoading = false, isError = false)
                        Log.i("Weapon-TAG", "${result.data}")
                        val adapter = WeaponsAdapter(result.data)
                        binding.rvWeapons.layoutManager = LinearLayoutManager(this@WeaponsActivity)
                        binding.rvWeapons.adapter = adapter
                    }
                }
            }
        }
    }


    private fun displayViewsMatchingState(isLoading : Boolean, isError : Boolean){
        if(isLoading){
            binding.rvWeapons.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            binding.tvNoWeaponFound.visibility = View.GONE
        }else{
            if(isError){
                binding.pbLoading.visibility = View.GONE
                binding.tvNoWeaponFound.visibility = View.VISIBLE
            }else{
                binding.rvWeapons.visibility = View.VISIBLE
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unloadKoinModules(viewModelModule)
    }
}