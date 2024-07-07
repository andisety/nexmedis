package com.example.nexmedis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nexmedis.R
import com.example.nexmedis.database.ProductEntity
import com.example.nexmedis.model.response.ResponseProductsItem

class ProductLocalAdapter(
    private val listProduct:List<ProductEntity>,
    private val listener:ListenerProductLocal
): RecyclerView.Adapter<ProductLocalAdapter.ViewHolder>()  {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        var itemName: TextView = view.findViewById(R.id.textTitle)
        val itemImage: ImageView = view.findViewById(R.id.imageProduct)
        val itemImageTrash: ImageView = view.findViewById(R.id.iv_trash)
        val itemCat: TextView = view.findViewById(R.id.textCategory)
        val itemPrice: TextView = view.findViewById(R.id.textPrice)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductLocalAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_fav, parent, false)
        return ProductLocalAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductLocalAdapter.ViewHolder, position: Int) {
        val item = listProduct[position]
        holder.itemName.text = item.title
        holder.itemCat.text = item.category
        holder.itemPrice.text = "$${item.price}"
        holder.itemImageTrash.setOnClickListener {
            listener.onKlik(item)
        }
        Glide.with(holder.itemView.context).load(item.image).into(holder.itemImage)
    }

    override fun getItemCount(): Int {
      return  listProduct.size
    }
    interface ListenerProductLocal{
        fun onKlik(product: ProductEntity)
    }
}

