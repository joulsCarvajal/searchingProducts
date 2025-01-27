package com.example.searchingproducts.ui.search.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchingproducts.R
import com.example.searchingproducts.data.remote.model.Product
import com.example.searchingproducts.databinding.ItemProductBinding
import java.text.NumberFormat

class ProductAdapter (
    private val onItemClick: (productId: String, categoryId: String?) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products = listOf<Product>()

    fun setProducts(newProducts: List<Product>){
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    private fun formatPrice(price: Double, currency: String): String {
        return when (currency) {
            "ARS" -> "$ ${NumberFormat.getNumberInstance().format(price)}"
            else -> "${currency} ${NumberFormat.getNumberInstance().format(price)}"
        }
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvTitle.text = product.title
                tvPrice.text = formatPrice(product.price, product.currency)

                val imageUrl = product.imageUrl.replace("http://http2", "https://http2")
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .placeholder(ColorDrawable(Color.LTGRAY))
                    .error(R.drawable.error_image)
                    .centerCrop()
                    .into(ivProduct)
            }

            binding.root.setOnClickListener {
                onItemClick(product.id, product.categoryId)
            }
        }
    }

}