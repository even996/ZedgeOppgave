package com.eveno.zedgeoppgave.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eveno.zedgeoppgave.R
import com.eveno.zedgeoppgave.models.Hit
import kotlinx.android.synthetic.main.item_images.view.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Hit>(){
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_images,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(image.largeImageURL).into(ivImage)
            tvSource.text = image.user
            tvTitle.text = image.user
            tvDescription.text = image.user
            tvPublishedAt.text = image.user
            setOnClickListener {
                onItemClickListener?.let { it(image) }

            }
        }
    }

    private var onItemClickListener: ((Hit) -> Unit)? = null

    fun setOnItemClickListener(listener: (Hit) -> Unit){
        onItemClickListener = listener
    }
}