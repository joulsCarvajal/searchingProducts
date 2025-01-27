package com.example.searchingproducts.ui

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.searchingproducts.R
import com.google.android.material.button.MaterialButton

class ErrorViewHelper(
    private val errorView: ViewGroup,
    private val onRetry: () -> Unit
) {
    fun showError(message: String) {
        errorView.apply {
            isVisible = true
            findViewById<TextView>(R.id.errorMessage).text = message
            findViewById<MaterialButton>(R.id.retryButton).setOnClickListener {
                onRetry()
            }
        }
    }

    fun hide() {
        errorView.isVisible = false
    }
}