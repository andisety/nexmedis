package com.example.nexmedis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nexmedis.R
import com.example.nexmedis.model.response.ResponseProductsItem


class ProductsAdapter(private val products: List<ResponseProductsItem>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemRating: RatingBar = view.findViewById(R.id.itemRating)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products[position]
        Glide.with(holder.itemView.context).load(item.image).into(holder.itemImage)
        holder.itemName.text = item.title
        holder.itemRating.rating = item.rating.rate.toFloat()
        holder.itemPrice.text = item.price.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
