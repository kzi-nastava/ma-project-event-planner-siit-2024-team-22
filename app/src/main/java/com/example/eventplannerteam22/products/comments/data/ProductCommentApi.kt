    package com.example.eventplannerteam22.products.comments.data

    import com.example.eventplannerteam22.products.comments.domain.ProductComment
    import retrofit2.http.Body
    import retrofit2.http.GET
    import retrofit2.http.POST
    import retrofit2.http.Path

    data class CreateProductCommentRequest(
        val userId: Int,
        val productId: Int,
        val text: String
    )

    interface ProductCommentApi {
        @GET("/products/comments/product/{productId}")
        suspend fun getComments(@Path("productId") productId: Int): List<ProductComment>

        @POST("/products/comments")
        suspend fun addComment(@Body comment: CreateProductCommentRequest): ProductComment
    }