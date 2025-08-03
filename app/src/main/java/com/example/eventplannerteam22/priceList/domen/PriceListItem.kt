package com.example.eventplannerteam22.priceList.domen

import java.math.BigDecimal

data class PriceListItem(
    val name: String,
    val price: BigDecimal,
    val discount: BigDecimal,
    val type: String // "PRODUCT" или "SOLUTION"
) {
    val discountedPrice: BigDecimal
        get() {
            if (price == BigDecimal.ZERO || discount == BigDecimal.ZERO) {
                return BigDecimal.ZERO
            }
            val discountAmount = price.multiply(discount.divide(BigDecimal.valueOf(100)))
            return price.subtract(discountAmount)
        }
}