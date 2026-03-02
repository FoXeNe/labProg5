package model

data class Coordinates(
    val x: Long, // Поле не может быть null
    val y: Float, // Максимальное значение поля: 519, Поле не может быть null
) {
    init {
        require(y <= 519) { "y должен быть меньше 519" }
    }
}
