package com.example.searchingproducts.ui.productlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchingproducts.R
import com.example.searchingproducts.databinding.FragmentProductListBinding
import com.example.searchingproducts.ui.search.SearchFragmentDirections
import com.example.searchingproducts.ui.search.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var binding: FragmentProductListBinding
    private val args: ProductListFragmentArgs by navArgs()
    private val adapter = ProductAdapter { productId, categoryId ->
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToDetailFragment(productId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.searchProducts(args.query, args.categoryId)
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.rvProducts.apply {
            adapter = this@ProductListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { response ->
            adapter.setProducts(response.results)
        }
    }


}