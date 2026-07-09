package com.example.productexplorer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.productexplorer.model.ProductUi

@Composable
fun ProductCatalogContainer(
    products: List<ProductUi>,
    categories: List<String>,
    onProductClick: (ProductUi) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    var showOnlyInStock by rememberSaveable {
        mutableStateOf(false)
    }
    var favoriteProductIds by rememberSaveable {
        mutableStateOf(listOf<Int>())
    }

    val filteredProducts = remember(products, searchQuery, showOnlyInStock) {
        products.filter { product ->
            val matchesSearch =
                product.title.contains(searchQuery, ignoreCase = true) ||
                        product.brand.contains(searchQuery, ignoreCase = true) ||
                        product.category.contains(searchQuery, ignoreCase = true)
            val matchesStock =
                !showOnlyInStock || product.stock > 0
            matchesSearch && matchesStock
        }
    }

    fun toggleFavorite(productId: Int) {
        favoriteProductIds = if (favoriteProductIds.contains(productId)) {
            favoriteProductIds - productId
        } else {
            favoriteProductIds + productId
        }
    }

    ProductCatalogScreen(
        products = filteredProducts,
        categories = categories,
        searchQuery = searchQuery,
        onSearchQueryChange = { newValue ->
            searchQuery = newValue
        },
        showOnlyInStock = showOnlyInStock,
        onToggleStockFilter = {
            showOnlyInStock = !showOnlyInStock
        },
        favoriteProductIds = favoriteProductIds,
        onFavoriteClick = { productId ->
            toggleFavorite(productId)
        },
        onProductClick = onProductClick,
        modifier = modifier
    )
}