package com.example.nexmedis.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexmedis.R
import com.example.nexmedis.adapter.GridAdapter
import com.example.nexmedis.adapter.GridItem
import androidx.lifecycle.ViewModelProvider
import com.example.nexmedis.adapter.ProductsAdapter
import com.example.nexmedis.databinding.FragmentHomeBinding
import com.example.nexmedis.model.ApiResult
import com.example.nexmedis.model.response.ResponseProductsItem


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var nexViewModel: NexViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nexViewModel = ViewModelProvider(this).get(NexViewModel::class.java)

        nexViewModel.products.observe(viewLifecycleOwner){products->
            when(products){
                is ApiResult.Success->{
                    showLoading(false)
                    setUpRecyclerView(products.data)
                }
                is ApiResult.Error->{
                    showLoading(false)
                    Toast.makeText(requireContext(),products.errorMessage,Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Loading->{
                    showLoading(true)
                }
            }
        }

    }
    fun setUpRecyclerView(products:List<ResponseProductsItem>){
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        productsAdapter= ProductsAdapter(products)
        recyclerView.adapter=productsAdapter
    }

    fun showLoading(isLoading: Boolean) {
        binding.pgBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}