package com.example.eventplannerteam22.products.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class UpdateProductDTO(
    val name: String = "",
    val description: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val discount: BigDecimal = BigDecimal.ZERO,
    val isPrivate: Boolean = false
) : Parcelable