package com.example.myexpertnews.ui.agents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.domain.model.Agent
import com.example.myexpertnews.databinding.FragmentFavoriteAgentsBinding
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteAgentsFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteAgentsBinding
    private val viewModel : FavoriteAgentsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteAgentsBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.favoriteAgents.collect{ result ->
                bindDataToViews(result)
            }
        }

        return binding.root
    }

    private fun bindDataToViews(agents : List<Agent>){
        if(agents.isEmpty()){
            binding.tvNoAgentFound.visibility = View.VISIBLE
            binding.rvAgents.visibility = View.GONE
        }else{
            binding.tvNoAgentFound.visibility = View.GONE
            binding.rvAgents.visibility = View.VISIBLE
            val adapter = AgentsAdapter(agents){agentId ->
//                val action = FavoriteAgentsFragmentDirections.actionFavoriteAgentsFragmentToDetailAgentFragment(agentId)
//                binding.root.findNavController().navigate(action)
            }
            binding.rvAgents.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.rvAgents.adapter = adapter
        }
    }

}