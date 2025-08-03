package com.example.eventplannerteam22.products.comments.data

import com.example.eventplannerteam22.products.comments.domain.ProductComment
import javax.inject.Inject

class ProductCommentRepository @Inject constructor(
    private val api: ProductCommentApi
) {
    suspend fun getComments(productId: Int): List<ProductComment> {
        return api.getComments(productId)
    }

    suspend fun addComment(token: String, userId: Int, productId: Int, text: String): ProductComment {
        return api.addComment("Bearer $token", CreateProductCommentRequest(userId, productId, text))
    }
}