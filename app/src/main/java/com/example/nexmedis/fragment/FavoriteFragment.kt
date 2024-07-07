package com.example.nexmedis.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
           setupRecyclerView(it)
            if (it.isEmpty()){
                binding.apply {
                    ivClear.visibility = View.GONE
                    tvClear.visibility =View.GONE
                    tvEmpty.visibility = View.VISIBLE
                }
            }else{
                binding.apply {
                    ivClear.visibility = View.VISIBLE
                    tvClear.visibility =View.VISIBLE
                    tvEmpty.visibility = View.GONE
                }
            }
        })

        binding.apply {
            ivClear.setOnClickListener {
                productViewModel.delleteAll()
            }
            tvClear.setOnClickListener {
                productViewModel.delleteAll()
            }
        }






    }
    fun setupRecyclerView( productEntity: List<ProductEntity>){
        recyclerView=binding.rcListFav
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        productLocalAdapter = ProductLocalAdapter(productEntity,object : ProductLocalAdapter.ListenerProductLocal{
            override fun onKlik(product: ProductEntity) {
               productViewModel.delete(product.id)
                Toast.makeText(requireContext(),"Success Delete",Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = productLocalAdapter
    }



}