package lab5.model

import java.time.ZonedDateTime

data class Product(
    val id: Long,
    val name: String,
    val coordinates: Coordinates,
    val creationDate: java.time.ZonedDateTime,
    val price: Long,
    val unitOfMeasure: UnitOfMeasure?, // Поле может быть null
    val manufacturer: Organization,
) : Comparable<Product> {
    override fun compareTo(other: Product): Int = this.price.compareTo(other.price)
}
