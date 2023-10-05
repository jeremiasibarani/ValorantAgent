package com.example.myexpertnews.ui.agents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.model.DetailAgent
import com.example.myexpertnews.R
import com.example.myexpertnews.databinding.FragmentDetailAgentBinding
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class DetailAgentFragment : Fragment() {

    private lateinit var binding : FragmentDetailAgentBinding
    private val viewModel : DetailAgentViewModel by viewModel()
    private val args : DetailAgentFragmentArgs by navArgs()
    private var agent : DetailAgent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailAgentBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.detailAgent.collect{result ->
                when(result){
                    is NetworkResult.Loading -> {
                        displayViewsMatchingState(isLoading = true, isError = false)
                    }
                    is NetworkResult.Error -> {
                        displayViewsMatchingState(isLoading = false, isError = true)
                        Log.i("Detail-TAG", result.message)
                        binding.tvAgentInfoNotFound.visibility = View.VISIBLE
                    }
                    is NetworkResult.Success -> {
                        displayViewsMatchingState(isLoading = false, isError = false)
                        bindDataToViews(result.data)
                    }
                }
            }
        }

        val agentId = args.agentId
        if(agentId.isNotEmpty()){
            viewModel.setAgentId(agentId)
            viewModel.checkFavoriteAgent(agentId).observe(viewLifecycleOwner){ existStatus ->
                if(existStatus == 1){
                    binding.fabAddFavorite.setImageResource(R.drawable.baseline_favorite_24)
                }else{
                    binding.fabAddFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                binding.fabAddFavorite.setOnClickListener{
                    if(existStatus == 1){
                        // Hapus dari database
                        viewModel.removeFavoriteAgent(agent!!)
                    }else{
                        // Tambah ke dalam database
                        viewModel.addFavoriteAgent(agent!!)
                    }
                }
            }
        }

        return binding.root
    }

    private fun bindDataToViews(detailAgent: DetailAgent){
        binding.apply {
            tvAgentName.text = detailAgent.displayName
            tvAgentType.text = detailAgent.role
            tvAgentDesc.text = detailAgent.description
            Glide.with(this@DetailAgentFragment)
                .load(detailAgent.imgUrl)
                .into(imgAgent)
        }
        agent = detailAgent
    }

    private fun displayViewsMatchingState(isLoading : Boolean, isError : Boolean){
        if(isLoading){
            binding.svAgentInfo.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            binding.tvAgentInfoNotFound.visibility = View.GONE
        }else{
            if(isError){
                binding.pbLoading.visibility = View.GONE
                binding.tvAgentInfoNotFound.visibility = View.VISIBLE
            }else{
                binding.svAgentInfo.visibility = View.VISIBLE
                binding.pbLoading.visibility = View.GONE
            }
        }
    }
}