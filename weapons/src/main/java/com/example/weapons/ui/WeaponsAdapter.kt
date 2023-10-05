package com.example.weapons.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.model.Weapon
import com.example.weapons.databinding.WeaponItemBinding

class WeaponsAdapter(private val weapons : List<Weapon>) : RecyclerView.Adapter<WeaponsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : WeaponItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(weapon : Weapon){
            binding.tvWeaponName.text = weapon.displayName
            Glide.with(binding.root.context)
                .load(weapon.imgUrl)
                .into(binding.imgWeapon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WeaponItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = weapons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weapons[position])
    }

}