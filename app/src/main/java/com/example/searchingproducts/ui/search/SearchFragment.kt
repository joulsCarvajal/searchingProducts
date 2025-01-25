package com.example.searchingproducts.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchingproducts.databinding.FragmentSearchBinding
import com.example.searchingproducts.ui.search.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val adapter = ProductAdapter { productId ->
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductListFragment(productId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*setupRecyclerView()*/
        setupSearchView()
        setupObservers()
    }

/*    private fun setupRecyclerView() {
        binding.rvProducts.apply {
            adapter = this@SearchFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }*/

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProductListFragment(it))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun setupObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { response ->
            adapter.setProducts(response.results)
        }
    }
}