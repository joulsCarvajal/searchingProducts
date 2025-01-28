package com.example.searchingproducts.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.searchingproducts.R
import com.example.searchingproducts.data.remote.model.ProductDetail
import com.example.searchingproducts.databinding.FragmentDetailScreenBinding
import com.example.searchingproducts.databinding.FragmentSearchBinding
import com.example.searchingproducts.databinding.LayoutErrorBinding
import com.example.searchingproducts.ui.ErrorViewHelper
import com.example.searchingproducts.ui.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailScreenBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.getProductById(args.productId)
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    updateUI(state.data)
                }

                is UiState.Error -> {
                    hideLoading()
                    showError(state.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.isVisible = true
            contentLayout.isVisible = false
            errorView.root.isVisible = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            progressBar.isVisible = false
            contentLayout.isVisible = true
        }
    }

    private fun showError(message: String) {
        binding.apply {
            errorView.root.isVisible = true
            contentLayout.isVisible = false
            errorView.errorMessage.text = message
            errorView.retryButton.setOnClickListener {
                viewModel.retry()
            }
        }
    }

    private fun updateUI(product: ProductDetail) {
        binding.apply {
            tvProductName.text = product.title
            Glide.with(requireContext())
                .load(product.imageUrl.replace("http://", "https://").replace("-I.jpg", "-F.jpg"))
                .centerCrop()
                .into(ivProductImageLarge)

            Glide.with(requireContext())
                .load(product.imageUrl.replace("http://", "https://"))
                .centerCrop()
                .into(ivProductImage)

            tvProductBrand.text =
                "Marca ${product.attributes.find { it.id == "BRAND" }?.value ?: product.attributes.find { it.id == "MANUFACTURER" }?.value ?: "Vendedor"}"
                    ?: "N/A"
            tvColor.text = product.attributes.find { it.id == "COLOR" }?.value ?: "N/A"
            tvCondition.text = product.attributes.find { it.id == "ITEM_CONDITION" }?.value ?: "N/A"
            tvModel.text = product.attributes.find { it.id == "MODEL" }?.value ?: "N/A"
            tvPrice.text = formatPrice(product.price, product.currency)
            btnBuy.text = getString(R.string.comprar)
            btnBuy.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(product.permalink)))
            }

        }
    }

    private fun formatPrice(price: Double, currency: String): String {
        return when (currency) {
            "ARS" -> "$ ${NumberFormat.getNumberInstance().format(price)}"
            else -> "${currency} ${NumberFormat.getNumberInstance().format(price)}"
        }
    }
}