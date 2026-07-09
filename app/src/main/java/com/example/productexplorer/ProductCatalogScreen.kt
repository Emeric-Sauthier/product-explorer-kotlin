package com.example.productexplorer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.productexplorer.components.product_catalog.CategoryRow
import com.example.productexplorer.components.product_catalog.ProductListItem
import com.example.productexplorer.model.ProductUi
import com.example.productexplorer.model.sampleCategories

@Composable
fun ProductCatalogScreen(
    products: List<ProductUi>,
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

    fun toggleFavorite(productId: Int) {
        favoriteProductIds = if (favoriteProductIds.contains(productId)) {
            favoriteProductIds - productId
        } else {
            favoriteProductIds + productId
        }
    }

    val filteredProducts = remember(products, searchQuery) {
        products.filter { product ->
            val matchesSearch = product.title.contains(searchQuery, ignoreCase = true) ||
                    product.brand.contains(searchQuery, ignoreCase = true) ||
                    product.category.contains(searchQuery, ignoreCase = true)

            val matchesStock = !showOnlyInStock || product.stock > 0

            matchesSearch && matchesStock
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Catalogue produits",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            Text(
                text = "Explorez une sélection de produits disponibles localement.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                },
                label = {
                    Text(text = "Rechercher un produit")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Button(
                onClick = {
                    showOnlyInStock = !showOnlyInStock
                }
            ) {
                Text(
                    text = if (showOnlyInStock) {
                        "Afficher tous les produits"
                    } else {
                        "Afficher uniquement les produits en stock"
                    }
                )
            }
        }

        item {
            Text(
                text = "${filteredProducts.size} produit(s) affiché(s)",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            CategoryRow(categories = sampleCategories())
        }

        items(
            items = filteredProducts,
            key = { product -> product.id }
        ) { product ->
            ProductListItem(
                product = product,
                isFavorite = favoriteProductIds.contains(product.id),
                onFavoriteClick = {
                    toggleFavorite(product.id)
                },
                onClick = {
                    onProductClick(product)
                }
            )
        }
    }
}