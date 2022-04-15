package com.fyznur.itunessearchcase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fyznur.itunessearchcase.R
import com.fyznur.itunessearchcase.data.model.ItemDetail
import com.fyznur.itunessearchcase.utils.localeDate
import com.fyznur.itunessearchcase.utils.parser

class MainListAdapter(
    val data: ArrayList<ItemDetail?>?,
    private val clickListener: RecyclerViewItemClick
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 0
    private val ITEM = 1
    private var isLoadingAdded = false

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val image: ImageView = view.findViewById(com.fyznur.itunessearchcase.R.id.image)
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.rv_item, parent, false)
                viewHolder = ItemViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val itemHolder = holder as ItemViewHolder
                val context = itemHolder.itemView.context
                itemHolder.txtName.text = data?.get(position)?.collectionName ?: "-"
                itemHolder.txtPrice.text =
                    (data?.get(position)?.collectionPrice
                        ?: 0.0).toString() + " " + data?.get(position)?.currency
                Glide.with(context)
                    .load(data?.get(position)?.artworkUrl100)
                    .into(itemHolder.image)
                val releaseDate = localeDate.format(parser.parse(data?.get(position)?.releaseDate))
                itemHolder.txtDate.text = releaseDate
                itemHolder.itemView.setOnClickListener {
                    clickListener.onItemClick(position)
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == ((data?.size ?: 0) - 1) && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ItemDetail(null,
            null,
            null,
            null,
            null,
            null,
            null,
            null))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = (data?.size ?: 0) - 1
        val result = getItem(position)
        if (result != null) {
            data?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun add(item: ItemDetail?) {
        data?.add(item)
        notifyItemInserted((data?.size ?: 0) - 1)
    }

    fun addAll(itemResults: ArrayList<ItemDetail?>?) {
        if (itemResults != null) {
            for (result in itemResults) {
                add(result)
            }
        }
    }
    fun clearAll(){
        data?.clear()
    }

    private fun getItem(position: Int): ItemDetail? {
        return data?.get(position)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}