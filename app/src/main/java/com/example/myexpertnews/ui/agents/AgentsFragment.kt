package com.example.myexpertnews.ui.agents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.source.remote.NetworkResult
import com.example.myexpertnews.databinding.FragmentAgentsBinding
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class AgentsFragment : Fragment() {

    private val viewModel : AgentsViewModel by viewModel()
    private lateinit var binding : FragmentAgentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAgentsBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.agents.collect{ result ->
                Log.d("Agents-TAG", "network result : $result")
                when(result){
                    is NetworkResult.Loading -> {
                        displayViewsMatchingState(isLoading = true, isError = false)
                    }
                    is NetworkResult.Error -> {
                        displayViewsMatchingState(isLoading = false, isError = true)
                        binding.tvNoAgentFound.visibility = View.VISIBLE
                        Log.d("Agents-TAG", "network result : ${result.message}")
                    }
                    is NetworkResult.Success -> {
                        if(result.data.isEmpty()){
                            displayViewsMatchingState(isLoading = false, isError = true)
                        }else{
                            displayViewsMatchingState(isLoading = false, isError = false)
                            binding.rvAgents.layoutManager = GridLayoutManager(requireActivity(), 2)
                            binding.rvAgents.adapter = AgentsAdapter(result.data){id ->

                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun displayViewsMatchingState(isLoading : Boolean, isError : Boolean){
        if(isLoading){
            binding.rvAgents.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            binding.tvNoAgentFound.visibility = View.GONE
        }else{
            if(isError){
                binding.pbLoading.visibility = View.GONE
                binding.tvNoAgentFound.visibility = View.VISIBLE
            }else{
                binding.rvAgents.visibility = View.VISIBLE
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

}