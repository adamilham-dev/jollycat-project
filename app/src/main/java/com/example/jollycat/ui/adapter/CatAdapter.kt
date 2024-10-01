package com.example.jollycat.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jollycat.R
import com.example.jollycat.data.model.Cat
import com.example.jollycat.ui.catdetail.CatDetailActivity
import com.example.jollycat.utils.Constants.formatToRupiah
import com.example.jollycat.databinding.RecyclerviewProductCardBinding
import com.example.jollycat.utils.Constants

class CatAdapter :
    RecyclerView.Adapter<CatAdapter.MainViewHolder>() {
    private var catList: List<Cat> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setCatList(catList: List<Cat>) {
        this.catList = catList
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val itemBinding: RecyclerviewProductCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(cat: Cat) {
            Glide.with(itemBinding.root)
                .load(cat.CatImage)
                .placeholder(
                    ContextCompat.getDrawable(
                        itemBinding.root.context,
                        R.drawable.jollycat_logo_blue_small
                    )
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemBinding.idIVCatImage)

            itemBinding.idTVCatName.text = cat.CatName
            itemBinding.desc.text = cat.CatDescription
            itemBinding.idTVPrice.text = formatToRupiah(cat.CatPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RecyclerviewProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val cat = catList[position]
        holder.bindItem(cat)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CatDetailActivity::class.java)
            intent.putExtra(CatDetailActivity.EXTRA_CAT, cat)
            it.context.startActivity(intent)
        }
    }
}