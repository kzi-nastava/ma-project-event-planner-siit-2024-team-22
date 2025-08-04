package com.example.eventplannerteam22.productcategory.data


import com.example.eventplannerteam22.productcategory.domen.ProductCategory
import javax.inject.Inject;

class ProductCategoryRepository @Inject constructor(
    private val productCategoryApi: ProductCategoryApi
) {
    suspend fun getProductCategories(limit: Int, offset: Int): List<ProductCategory> {
        return productCategoryApi.getProductCategories(limit, offset)
    }
    suspend fun getProductCategoryById(id: Int): ProductCategory {
        return productCategoryApi.getProductCategoryById(id)
    }
    suspend fun addProductCategory(solutionCategory: ProductCategory) {
        return productCategoryApi.addProductCategory(solutionCategory)
    }

    suspend fun updateProductCategory(solutionCategory: ProductCategory, id: Int) {
        return productCategoryApi.updateProductCategory(solutionCategory, id)
    }

    suspend fun deleteProductCategory(id: Int) {
        return productCategoryApi.deleteProductCategory(id)
    }
}
