package com.rxmobileteam.lecture2_3.products

enum class ProductCategory {
  LAPTOP,
  PHONE,
  HEADPHONES,
  SMART_WATCH,
  CAMERA,
}

data class Product(
  val id: String,
  val name: String,
  val price: Double,
  val category: ProductCategory,
  val favoriteCount: Int,
)

data class Order(
  val id: String,
  val products: List<Product>,
  val isDelivered: Boolean,
)

// Return a list of Product, sorted in the ascending by price. if prices are equal, sorted by favoriteCount descending
fun List<Product>.sortedByPriceAscendingThenByFavoriteCountDescending(): List<Product> {
  return this.sortedWith(compareBy<Product> { it.price }.thenByDescending { it.favoriteCount })
}

//  Return a list of Products in the orders, duplicates are allowed.
fun List<Order>.getProductsList(): List<Product> = this.flatMap { it.products }

//  Return a set of Products in the orders (The order doesn't matter).
fun List<Order>.getProductsSet(): Set<Product> = this.getProductsList().toSet()

//  Return a list of delivered orders
fun List<Order>.getDeliveredOrders(): List<Order> = this.filter { it.isDelivered }

//  Return a list of products in the delivered orders
fun List<Order>.getDeliveredProductsList(): List<Product> = this.getDeliveredOrders().flatMap { it.products }

//  Partition the orders into two lists: "delivered" and "not delivered"
fun List<Order>.partitionDeliveredAndNotDelivered(): Pair<List<Order>, List<Order>> = this.partition { it.isDelivered }

//  Return a map of product to count of this product in the orders
//  Eg: [Product1 -> 2, Product2 -> 1, Product3 -> 3]
fun List<Order>.countOfEachProduct(): Map<Product, Int> = this.getProductsList().groupingBy { it }.eachCount()

//  Return the sum of product prices in the order
fun Order.sumProductPrice(): Double = this.products.sumOf { it.price }

//  Return the product with the maximum price in the order
fun Order.getMaxPriceProduct(): Product = this.products.maxWith(compareBy { it.price })

//  Return the product with the min price in the order
fun Order.getMinPriceProduct(): Product = this.products.minWith(compareBy { it.price })

