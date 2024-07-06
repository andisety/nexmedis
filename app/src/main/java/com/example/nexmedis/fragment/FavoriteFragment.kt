package com.example.nexmedis.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexmedis.R
import com.example.nexmedis.adapter.ProductLocalAdapter
import com.example.nexmedis.database.ProductEntity
import com.example.nexmedis.databinding.FragmentFavoriteBinding
import com.example.nexmedis.databinding.FragmentHomeBinding


class FavoriteFragment : Fragment() {
    private var _binding:FragmentFavoriteBinding?=null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel
    lateinit var productLocalAdapter: ProductLocalAdapter
    private lateinit var recyclerView: RecyclerView
    private var listProduct= emptyList<ProductEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel= ViewModelProvider(this).get(ProductViewModel::class.java)

        productViewModel.allData.observe(viewLifecycleOwner, Observer {
//            listProduct=it
           setupRecyclerView(it)
        })






    }
    fun setupRecyclerView( productEntity: List<ProductEntity>){
        recyclerView=binding.rcListFav
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        productLocalAdapter = ProductLocalAdapter(productEntity,object : ProductLocalAdapter.ListenerProductLocal{
            override fun onKlik(product: ProductEntity) {
                TODO("Not yet implemented")
            }
        })
        recyclerView.adapter = productLocalAdapter
    }



}