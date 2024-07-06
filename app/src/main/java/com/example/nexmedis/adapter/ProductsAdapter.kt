package com.example.nexmedis.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nexmedis.R
import com.example.nexmedis.model.response.ResponseProductsItem
import com.squareup.picasso.Picasso


class ProductsAdapter(
    private var products: List<ResponseProductsItem>,
    private val listener:ListenerMovePage
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemImageBg: RelativeLayout = view.findViewById(R.id.bgImg)
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
        val bgRes = if (position % 2 == 0) R.drawable.bg_left else R.drawable.bg_right
        holder.itemImageBg.setBackgroundResource(bgRes)

        val item = products[position]

//        Glide.with(holder.itemView.context)
//            .load(item.image)
//            .into(holder.itemImage)
        Picasso.get()
            .load(item.image)
            .into(holder.itemImage)
        holder.itemName.text = item.title
        holder.itemRating.rating = item.rating.rate.toFloat()
        holder.itemPrice.text = "$${item.price.toString()}"
        holder.itemView.setOnClickListener {
            listener.onKlik(item)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
    fun filterList(filteredList: List<ResponseProductsItem>) {
        products = filteredList
        Log.d("GA<BAR", filteredList[0].image)
        notifyDataSetChanged()
    }

    interface ListenerMovePage{
        fun onKlik(product:ResponseProductsItem)
    }

}
