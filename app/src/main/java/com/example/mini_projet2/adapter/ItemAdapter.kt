package com.example.mini_projet2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet2.databinding.RvItemBinding
import com.example.mini_projet2.model.Info

class ItemAdapter(private val context:Context, private val dataset: MutableList<Info>):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val bindingItem: RvItemBinding):RecyclerView.ViewHolder(bindingItem.root) {
        val tvNumero:TextView = bindingItem.tvNumero
        val imgUn:ImageView = bindingItem.imgUn
        val imgDeux:ImageView = bindingItem.imgDeux
        val imgTrois:ImageView = bindingItem.imgTrois
        val tvInfos:TextView = bindingItem.tvInfos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val bindingAdapter = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bindingAdapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.tvNumero.text = item.indexNum.toString()
        holder.imgUn.setImageResource(item.imgUn)
        holder.imgDeux.setImageResource(item.imgDeux)
        holder.imgTrois.setImageResource(item.imgTrois)
    }

    override fun getItemCount(): Int {
        // override fun getItemCount() = dataset.size
        return dataset.size
    }
}