package com.example.myexpertnews.ui.agents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.Agent
import com.example.myexpertnews.databinding.AgentLayoutBinding

class AgentsAdapter(
    private val agents : List<Agent>,
    private val onClickAction : (String) -> Unit
) : RecyclerView.Adapter<AgentsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding : AgentLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(agent : Agent){
            binding.tvAgentName.text = agent.displayName
            Glide.with(binding.root.context)
                .load(agent.imgUrl)
                .into(binding.imgAgent)
            binding.root.setOnClickListener {
                onClickAction(agent.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AgentLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = agents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(agents[position])
    }
}