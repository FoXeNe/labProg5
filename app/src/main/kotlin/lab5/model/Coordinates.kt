package lab5.model

data class Coordinates(
    val x: Long, // не может быть null
    val y: Float, // максимальное значение 519, не может быть null
) {
    init {
        require(y <= 519) { "y должен быть меньше 519" }
    }
}
