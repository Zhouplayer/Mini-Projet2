package com.example.mini_projet2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet1.databinding.RvItemBinding
import com.example.mini_projet2.model.Info

class ItemAdapter(private val context:Context, private val dataset: MutableList<Info>):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private lateinit var binding: RvItemBinding

    class ItemViewHolder(val bindingItem: RvItemBinding):RecyclerView.ViewHolder(bindingItem.root) {
        val tvNumero:TextView = bindingItem.tvNumero
        val imgUn:ImageView = bindingItem.imgUn
        val imgDeux:ImageView = bindingItem.imgDeux
        val imgTrois:ImageView = bindingItem.imgTrois
        val tvInfos:TextView = bindingItem.tvInfos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        var bindingItem = RvItemBinding.inflate(layoutInflater)
        val bindingAdapter = bindingItem.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bindingAdapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        // override fun getItemCount() = dataset.size
        return dataset.size
    }
}