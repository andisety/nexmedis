package com.example.nexmedis.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexmedis.R
import com.example.nexmedis.adapter.GridAdapter
import com.example.nexmedis.adapter.GridItem
import androidx.lifecycle.ViewModelProvider
import com.example.nexmedis.adapter.ProductsAdapter
import com.example.nexmedis.database.ProductEntity
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
    private lateinit var producViewModel:ProductViewModel
    private lateinit var  chipGroup: ChipGroup
    private var listId= emptyList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nexViewModel = ViewModelProvider(this)[NexViewModel::class.java]
        producViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        setUpRecyclerView()
        setUpChipGroup()

        producViewModel.getIdProduct().observe(viewLifecycleOwner){listIdProduct->
            listId=listIdProduct
        }

        val searchView=binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.wrapperChipGrp.visibility = View.GONE
                if (query!=null){
                    val products = (nexViewModel.products.value as? ApiResult.Success)?.data
                    if (products != null) {
                        searchByTitle(query,products)
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {

                }
                return true
            }
        })
        binding.searchView.setOnCloseListener {
            binding.wrapperChipGrp.visibility = View.VISIBLE
            nexViewModel.getProducts()
            chipGroup.clearCheck()
            true
        }

        nexViewModel.products.observe(viewLifecycleOwner){products->
            when(products){
                is ApiResult.Success->{
                    Log.d("ProductFragment", "Products received: ${products.data.size}")
                    showLoading(false)
                    setUpRecyclerView()
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
    }
        private fun setUpChipGroup() {
         chipGroup = binding.chipGroup
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            val products = (nexViewModel.products.value as? ApiResult.Success)?.data ?: return@setOnCheckedChangeListener
            Log.d("ProductFragment", "Chip checked: $checkedId")
            when (checkedId) {
                R.id.chip_all -> productsAdapter.filterList(products)
                R.id.chip_fav->filterById(listId,products)
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
    private fun filterById(listId: List<Int>,products:List<ResponseProductsItem>) {
            val filteredList = products.filter {products->
                products.id in listId }
        productsAdapter.filterList(filteredList)
    }

    private fun searchByTitle(title: String,products:List<ResponseProductsItem>) {
        val filteredList = products.filter { it.title.contains(title,ignoreCase = true)}
        Log.d("ProductFragment", "search list size: ${filteredList.size}")
        productsAdapter.filterList(filteredList)
    }

    fun showLoading(isLoading: Boolean) {
        binding.pgBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}