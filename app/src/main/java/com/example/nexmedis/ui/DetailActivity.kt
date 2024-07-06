package com.example.nexmedis.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.nexmedis.R
import com.example.nexmedis.databinding.ActivityDetailBinding
import com.example.nexmedis.model.response.ResponseProductsItem
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = intent.getSerializableExtra("DATA") as ResponseProductsItem
        Log.e("GAMBAR",product.image)

        binding.apply {
            tvdTitle.text = product.title
            tvDCat.text = product.category
            tvDPrice.text = "$${product.price.toString()}"
            tvDDesc.text = product.description
            Picasso.get()
                .load(product.image)
                .into(ivDProduct)
            ivBack.setOnClickListener { finish() }
        }

    }
}