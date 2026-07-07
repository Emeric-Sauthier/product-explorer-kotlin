package com.example.productexplorer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.productexplorer.components.product_home.CategoriesSection
import com.example.productexplorer.components.product_home.DailyOffer
import com.example.productexplorer.components.product_home.FeaturedProductSection
import com.example.productexplorer.components.product_home.HomeHeader
import com.example.productexplorer.components.product_home.SearchPreviewBar
import com.example.productexplorer.model.ProductUi

@Composable
fun ProductHomeScreen(
    featuredProduct: ProductUi,
    onFeaturedProductClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HomeHeader()
        Spacer(modifier = Modifier.height(24.dp))

        SearchPreviewBar()
        Spacer(modifier = Modifier.height(24.dp))

        FeaturedProductSection(
            product = featuredProduct,
            onClick = onFeaturedProductClick
        )
        Spacer(modifier = Modifier.height(24.dp))

        CategoriesSection()
        Spacer(modifier = Modifier.height(24.dp))

        DailyOffer()
    }
}