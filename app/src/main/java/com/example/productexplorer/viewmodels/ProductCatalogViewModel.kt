package com.example.productexplorer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.productexplorer.model.ProductUi
import com.example.productexplorer.model.sampleCategories
import com.example.productexplorer.model.sampleProducts

class ProductCatalogViewModel : ViewModel() {
    private val allProducts: List<ProductUi> = sampleProducts()
    val categories: List<String> = sampleCategories()

    var searchQuery by mutableStateOf("")
        private set
    fun onSearchQueryChange(newValue: String) {
        searchQuery = newValue
    }

    var showOnlyInStock by mutableStateOf(false)
        private set
    fun onToggleStockFilter() {
        showOnlyInStock = !showOnlyInStock
    }

    var favoriteProductIds by mutableStateOf(listOf<Int>())
        private set
    fun onFavoriteClick(productId: Int) {
        favoriteProductIds = if (favoriteProductIds.contains(productId)) {
            favoriteProductIds - productId
        } else {
            favoriteProductIds + productId
        }
    }

    val filteredProducts: List<ProductUi>
        get() = allProducts.filter { product ->
            val matchesSearch =
                product.title.contains(searchQuery, ignoreCase = true) ||
                        product.brand.contains(searchQuery, ignoreCase = true) ||
                        product.category.contains(searchQuery, ignoreCase = true)
            val matchesStock =
                !showOnlyInStock || product.stock > 0
            matchesSearch && matchesStock
        }
}