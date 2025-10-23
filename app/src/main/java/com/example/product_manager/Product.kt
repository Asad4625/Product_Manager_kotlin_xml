package com.example.product_manager

data class Product(
    var name: String,
    var price: Double,
    var quantity: Int
) {
    val tax: Double
        get() = price * 0.16

    val totalPriceWithTax: Double
        get() = (price + tax) * quantity
}
