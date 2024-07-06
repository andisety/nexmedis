package com.example.nexmedis.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.nexmedis.database.ProductEntity
import com.example.nexmedis.databinding.ActivityDetailBinding
import com.example.nexmedis.fragment.ProductViewModel
import com.example.nexmedis.model.response.Product
import com.example.nexmedis.model.response.ResponseProductsItem
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = intent.getSerializableExtra("DATA") as ResponseProductsItem
        productViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)

        binding.apply {
            tvdTitle.text = product.title
            tvDCat.text = product.category
            tvDPrice.text = "$${product.price.toString()}"
            tvDDesc.text = product.description
            Picasso.get()
                .load(product.image)
                .into(ivDProduct)
            ivBack.setOnClickListener { finish() }

            ivFav.setOnClickListener {
                val p = ProductEntity(product.id,product.category,product.description,product.image,product.price,product.title,product.rating.rate)
                try {
                    productViewModel.insert(p)
                    Toast.makeText(this@DetailActivity,"Sukses Add Favorite",Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Log.e("DATABASE",e.message.toString())
                }

            }
        }

    }
}