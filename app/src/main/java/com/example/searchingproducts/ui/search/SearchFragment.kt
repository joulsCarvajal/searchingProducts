package com.example.searchingproducts.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.searchingproducts.databinding.FragmentSearchBinding
import com.example.searchingproducts.ui.search.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.searchingproducts.R
import com.example.searchingproducts.data.remote.model.SearchResponse
import com.example.searchingproducts.ui.UiState
import com.google.android.material.chip.Chip

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get () = _binding!!
    private val adapter = ProductAdapter { productId, categoryId ->
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductListFragment(productId, categoryId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupCategoryButtons()
        setupObservers()
        setupRecentSearches()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchProducts(it)
                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProductListFragment(it, null))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun setupCategoryButtons(){
        with(binding) {
            buttonElectronics.setOnClickListener{
                navigateToProductList("MLA1055")
            }
            buttonClothing.setOnClickListener {
                navigateToProductList("MLA1430")
            }
            buttonHome.setOnClickListener {
                navigateToProductList("MLA1574")
            }
            buttonSports.setOnClickListener {
                navigateToProductList("MLA1276")
            }
        }
    }

    private fun navigateToProductList(categoryId: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductListFragment(
                query = "",
                categoryId = categoryId
            )
        )
    }

    private fun setupRecentSearches() {
        viewModel.recentSearches.observe(viewLifecycleOwner) { searches ->
            binding.recentSearchesChipGroup.removeAllViews()

            searches.forEach { search ->
                val chip = Chip(requireContext()).apply {
                    text = search
                    isClickable = true
                    setChipBackgroundColorResource(R.color.dark_surface)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    chipStartPadding = resources.getDimension(R.dimen.chip_padding)
                    chipEndPadding = resources.getDimension(R.dimen.chip_padding)
                    setOnClickListener {
                        viewModel.searchProducts(search)
                        findNavController().navigate(
                            SearchFragmentDirections.actionSearchFragmentToProductListFragment(
                                search, null
                            )
                        )
                    }
                }
                binding.recentSearchesChipGroup.addView(chip)
            }
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> showResults(state.data)
                is UiState.Error -> showError(state.message)
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            errorView.isVisible = false
            categoriesGrid.isVisible = false
        }
    }

    private fun showResults(response: SearchResponse) {
        binding.apply {
            progressBar.isVisible = false
            errorView.isVisible = false
            adapter.setProducts(response.results)
        }
    }

    private fun showError(message: String) {
        binding.apply {
            progressBar.isVisible = false
            errorView.isVisible = true
            categoriesGrid.isEnabled = true
            errorView.text = message
            retryButton.setOnClickListener {
                viewModel.retryLastSearch()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}