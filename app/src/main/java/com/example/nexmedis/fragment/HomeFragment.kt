package com.example.nexmedis.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.nexmedis.ui.DetailActivity
import com.google.android.material.chip.ChipGroup


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
        setUpRecyclerView()
        setUpChipGroup()

        nexViewModel.products.observe(viewLifecycleOwner){products->
            when(products){
                is ApiResult.Success->{
                    Log.d("ProductFragment", "Products received: ${products.data.size}")
                    showLoading(false)
                    // Init RecyclerView once
                    setUpRecyclerView()
                    // Set filtered products to full list initially
                    nexViewModel.filterProducts("all")

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
        nexViewModel.filteredProducts.observe(viewLifecycleOwner) { filteredProducts ->
            Log.d("ProductFragment", "Filtered Products received: ${filteredProducts.size}")
            productsAdapter.filterList(filteredProducts)
        }



    }

private fun setUpRecyclerView() {
    val recyclerView = binding.recyclerView
    recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    productsAdapter = ProductsAdapter(emptyList(),object:ProductsAdapter.ListenerMovePage{
        override fun onKlik(product: ResponseProductsItem) {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("DATA",product)
            startActivity(intent)
        }

    })
    recyclerView.adapter = productsAdapter

    val chipGroup: ChipGroup = binding.chipGroup
    chipGroup.setOnCheckedChangeListener { _, checkedId ->
        Log.d("ProductFragment", "Chip checked: $checkedId")
        when (checkedId) {
            R.id.chip_all -> nexViewModel.filterProducts("all")
            R.id.chip_electronics -> nexViewModel.filterProducts("electronics")
            R.id.chip_jewelery -> nexViewModel.filterProducts("jewelery")
            R.id.chip_mens_clothing -> nexViewModel.filterProducts("men's clothing")
            R.id.chip_womens_clothing -> nexViewModel.filterProducts("women's clothing")
        }
    }
}
        private fun setUpChipGroup() {
        val chipGroup: ChipGroup = binding.chipGroup
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            val products = (nexViewModel.products.value as? ApiResult.Success)?.data ?: return@setOnCheckedChangeListener
            Log.d("ProductFragment", "Chip checked: $checkedId")
            when (checkedId) {
                R.id.chip_all -> productsAdapter.filterList(products)
                R.id.chip_electronics -> filterByCategory("electronics", products)
                R.id.chip_jewelery -> filterByCategory("jewelery", products)
                R.id.chip_mens_clothing -> filterByCategory("men's clothing", products)
                R.id.chip_womens_clothing -> filterByCategory("women's clothing", products)
            }
        }
    }

    private fun filterByCategory(category: String,products:List<ResponseProductsItem>) {
        val filteredList = products.filter { it.category == category }
        Log.d("ProductFragment", "Filtered list size: ${filteredList.size}")
        productsAdapter.filterList(filteredList)
    }


    fun showLoading(isLoading: Boolean) {
        binding.pgBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}